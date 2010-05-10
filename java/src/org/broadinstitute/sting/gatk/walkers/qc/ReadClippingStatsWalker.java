/*
 * Copyright (c) 2009 The Broad Institute
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  �les (the �Software�), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, sub ject to the following
 *  conditions:
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *  THE SOFTWARE IS PROVIDED �AS IS�, WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  * OTHER DEALINGS IN THE SOFTWARE.
 */

package org.broadinstitute.sting.gatk.walkers.qc;

import org.broadinstitute.sting.gatk.walkers.Requires;
import org.broadinstitute.sting.gatk.walkers.DataSource;
import org.broadinstitute.sting.gatk.walkers.ReadWalker;
import org.broadinstitute.sting.gatk.refdata.ReadMetaDataTracker;
import org.broadinstitute.sting.utils.StingException;
import org.broadinstitute.sting.utils.Utils;
import org.broadinstitute.sting.utils.MathUtils;
import org.broadinstitute.sting.utils.sam.AlignmentUtils;
import org.broadinstitute.sting.commandline.Argument;
import net.sf.samtools.*;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 * User: depristo
 * Date: May 5, 2010
 * Time: 12:16:41 PM
 */

/**
 * Walks over the input reads, printing out statistics about the read length, number of clipping events, and length
 * of the clipping to the output stream.
 */
@Requires({DataSource.READS})
public class ReadClippingStatsWalker extends ReadWalker<ReadClippingStatsWalker.ReadClippingInfo,Integer> {
    @Argument(fullName="mappedOnly", shortName="mo", doc="when this flag is set (default), statistics will be collected "+
                "on mapped reads only, while unmapped reads will be discarded", required=false)
    protected boolean MAPPED_ONLY = true;

    @Argument(fullName="skip", shortName="skip", doc="When provided, only every skip reads are analyzed", required=false)
    protected int SKIP = 1;

//    public void initialize() {
//
//    }

    public class ReadClippingInfo {
        SAMReadGroupRecord rg;
        int readLength, nClippingEvents, nClippedBases;
    }

    public ReadClippingInfo map(char[] ref, SAMRecord read, ReadMetaDataTracker metaDataTracker) {
        if ( AlignmentUtils.isReadUnmapped(read) && MAPPED_ONLY)
            return null;

        ReadClippingInfo info = new ReadClippingInfo();
        info.rg = read.getReadGroup();

        if ( info.rg == null ) throw new StingException("Read "+read.getReadName()+" is not assigned to any read group");

        for ( CigarElement elt : read.getCigar().getCigarElements() ) {
            if ( elt.getOperator() != CigarOperator.N )

            switch ( elt.getOperator()) {
                case H : // ignore hard clips
                case S : // soft clip
                    info.nClippingEvents++;
                    info.nClippedBases += elt.getLength();
                    // note the fall through here
                case M :
                case D : // deletion w.r.t. the reference
                case P : // ignore pads
                case I : // insertion w.r.t. the reference
                    info.readLength += elt.getLength(); // Unless we have a reference skip, the read gets longer
                    break;
                case N : // reference skip (looks and gets processed just like a "deletion", just different logical meaning)
                    break;
                default : throw new IllegalStateException("Case statement didn't deal with cigar op: " + elt.getOperator());
            }
        }

        return info;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Provide an initial value for reduce computations.
     *
     * @return Initial value of reduce.
     */
    public Integer reduceInit() {
        out.println(Utils.join(" \t", Arrays.asList("ReadGroup", "ReadLength", "NClippingEvents", "NClippedBases", "PercentClipped")));
        return 0;
    }

    /**
     * Reduces a single map with the accumulator provided as the ReduceType.
     *
     * @param info  result of the map.
     * @param sum   accumulator for the reduce.
     * @return accumulator with result of the map taken into account.
     */
    public Integer reduce(ReadClippingInfo info, Integer sum) {
        if ( info != null ) {
            if ( sum % SKIP == 0 ) {
                String id = info.rg.getReadGroupId();
                out.printf("%s\t %d\t %d\t %d\t %.2f%n",
                        id, info.readLength, info.nClippingEvents, info.nClippedBases,
                        100.0 * MathUtils.ratio(info.nClippedBases, info.readLength));
            }
            return sum + 1;  //To change body of implemented methods use File | Settings | File Templates.
        } else {
            return sum;
        }
    }

    public void onTraversalDone(Integer result) {

    }
}