package org.broadinstitute.sting.playground.gatk.walkers.variantoptimizer;

import org.broadinstitute.sting.gatk.contexts.AlignmentContext;
import org.broadinstitute.sting.gatk.contexts.ReferenceContext;
import org.broadinstitute.sting.gatk.refdata.RefMetaDataTracker;
import org.broadinstitute.sting.gatk.walkers.RodWalker;
import org.broadinstitute.sting.utils.ExpandingArrayList;
import org.broadinstitute.sting.utils.StingException;
import org.broadinstitute.sting.utils.cmdLine.Argument;

/*
 * Copyright (c) 2010 The Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * Applies calibrated variant cluster parameters to variant calls to produce an accurate and informative variant quality score
 *
 * @author rpoplin
 * @since Mar 17, 2010
 *
 * @help.summary Applies calibrated variant cluster parameters to variant calls to produce an accurate and informative variant quality score
 */

public class ApplyVariantClustersWalker extends RodWalker<ExpandingArrayList<VariantDatum>, ExpandingArrayList<VariantDatum>> {

    /////////////////////////////
    // Command Line Arguments
    /////////////////////////////
    //@Argument(fullName="target_titv", shortName="titv", doc="The target Ti/Tv ratio towards which to optimize. (~~2.2 for whole genome experiments)", required=false)
    //private double TARGET_TITV = 2.1;
    @Argument(fullName="desired_num_variants", shortName="dV", doc="The desired number of variants to keep in a theoretically filtered set", required=false)
    private int DESIRED_NUM_VARIANTS = 0;
    @Argument(fullName="ignore_input_filters", shortName="ignoreFilters", doc="If specified the optimizer will use variants even if the FILTER column is marked in the VCF file", required=false)
    private boolean IGNORE_INPUT_FILTERS = false;
    @Argument(fullName="output_prefix", shortName="output", doc="The prefix added to output VCF file name and optimization curve pdf file name", required=false)
    private String OUTPUT_PREFIX = "optimizer";
    @Argument(fullName="clusterFile", shortName="clusterFile", doc="The output cluster file", required=true)
    private String CLUSTER_FILENAME = "optimizer.cluster";
    //@Argument(fullName = "optimization_model", shortName = "om", doc = "Optimization calculation model to employ -- GAUSSIAN_MIXTURE_MODEL is currently the default, while K_NEAREST_NEIGHBORS is also available for small callsets.", required = false)
    private VariantOptimizationModel.Model OPTIMIZATION_MODEL = VariantOptimizationModel.Model.GAUSSIAN_MIXTURE_MODEL;
    @Argument(fullName = "path_to_Rscript", shortName = "Rscript", doc = "The path to your implementation of Rscript. For Broad users this is probably /broad/tools/apps/R-2.6.0/bin/Rscript", required = false)
    private String PATH_TO_RSCRIPT = "/broad/tools/apps/R-2.6.0/bin/Rscript";
    @Argument(fullName = "path_to_resources", shortName = "resources", doc = "Path to resources folder holding the Sting R scripts.", required = false)
    private String PATH_TO_RESOURCES = "R/";

    /////////////////////////////
    // Private Member Variables
    /////////////////////////////
    private final ExpandingArrayList<String> annotationKeys = new ExpandingArrayList<String>();

    //---------------------------------------------------------------------------------------------------------------
    //
    // initialize
    //
    //---------------------------------------------------------------------------------------------------------------

    public void initialize() {
        if( !PATH_TO_RESOURCES.endsWith("/") ) { PATH_TO_RESOURCES = PATH_TO_RESOURCES + "/"; }

        VariantOptimizationModel theModel = null;
        switch (OPTIMIZATION_MODEL) {
            case GAUSSIAN_MIXTURE_MODEL:
                theModel = new VariantGaussianMixtureModel( CLUSTER_FILENAME );
                break;
            //case K_NEAREST_NEIGHBORS:
            //    theModel = new VariantNearestNeighborsModel( dataManager, TARGET_TITV, NUM_KNN );
            //    break;
            default:
                throw new StingException( "Variant Optimization Model is unrecognized. Implemented options are GAUSSIAN_MIXTURE_MODEL and K_NEAREST_NEIGHBORS" );
        }
    }

    //---------------------------------------------------------------------------------------------------------------
    //
    // map
    //
    //---------------------------------------------------------------------------------------------------------------

    public ExpandingArrayList<VariantDatum> map( RefMetaDataTracker tracker, ReferenceContext ref, AlignmentContext context ) {
        final ExpandingArrayList<VariantDatum> mapList = new ExpandingArrayList<VariantDatum>();
        return mapList;
    }

    //---------------------------------------------------------------------------------------------------------------
    //
    // reduce
    //
    //---------------------------------------------------------------------------------------------------------------

     public ExpandingArrayList<VariantDatum> reduceInit() {
        return new ExpandingArrayList<VariantDatum>();
    }

    public ExpandingArrayList<VariantDatum> reduce( final ExpandingArrayList<VariantDatum> mapValue, final ExpandingArrayList<VariantDatum> reduceSum ) {
        reduceSum.addAll( mapValue );
        return reduceSum;
    }

    public void onTraversalDone( ExpandingArrayList<VariantDatum> reduceSum ) {

        final VariantDataManager dataManager = new VariantDataManager( reduceSum, annotationKeys );
    }
}
