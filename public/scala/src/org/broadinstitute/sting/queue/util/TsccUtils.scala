
/* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
* OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
* HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
* THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package org.broadinstitute.sting.queue.util

import java.io.File
import collection.JavaConversions._

object TsccUtils {

    def starGenomeLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/star_sjdb"
   }else if(genome == "mm9") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm9/star"
   }else if(genome == "mm10") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm10/star"
   }else if(genome == "ce10") {
      retval = "/thunderstorm/users/mlovci/Genomes/ce10/star"
   }else if(genome == "dm3") {
      retval = "/thunderstorm/users/mlovci/Genomes/dm3/star"
   }
   retval
  }

  def chromSizeLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/hg19.chrom.sizes"
   }else if(genome == "mm9") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm9/mm9.chrom.sizes"
   }else if(genome == "mm10") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm10/mm10.chrom.sizes"
   }else if(genome == "ce10") {
      retval = "/thunderstorm/users/mlovci/Genomes/ce10/ce10.chrom.sizes"
   }else if(genome == "dm3") {
      retval = "/thunderstorm/users/mlovci/Genomes/dm3/dm3.chrom.sizes"
   }

   retval
  }

  def regionsLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/annotations/hg19data4"
   }else if(genome == "mm9") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm9/mm9data4"
   }else if(genome == "mm10") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm10/mm10data4"
   }

   retval
  }

  def asStructureLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/annotations/hg19data4"
   }else if(genome == "mm9") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm9/mm9data4"
   }else if(genome == "mm10") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm10/mm10data4"
   }


   retval
  }


  def genomeLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/chromosomes/all.fa"
   }else if(genome == "mm9") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm9/chromosomes/all.fa"
   }else if(genome == "mm10") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm10/chromosomes/all.fa"
   }else if(genome == "dm3") {
      retval = "/thunderstorm/users/mlovci/Genomes/dm3/chromosomes/all.fa"
   }
   retval
  }

  def phastconsLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/hg19_phastcons.bw"
   }else if(genome == "mm9") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm9/mm9_phastcons.bw"
   }else if(genome == "mm10") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm10/mm10_phastcons.bw"
   }

   retval
  }

  def genicRegionsLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/home/gpratt/clipper/clipper/data/regions/hg19_genes.bed"
   }else if(genome == "mm9") {
	retval = "/home/gpratt/clipper/clipper/data/regions/mm9_genes.bed"
   }else if(genome == "mm10") {
      retval = "/home/gpratt/clipper/clipper/data/region/mm10_genes.bed"
   }
   else if(genome == "ce10") {
      retval = "/home/gpratt/clipper/clipper/data/regions/ce10_genes.bed"
   }
   retval
  }

  def gffDbLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/annotations/gencode.v19.annotation.gtf.db"
   }else if(genome == "mm9") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm9/Mus_musculus.NCBIM37.64.fixed.gtf.db"
   }else if(genome == "mm10") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm10/annotations/gencode.vM3.annotation.gtf.db"
   }


   retval
  }

  def gffLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/annotations/gencode.v19.annotation.gtf"
   }else if(genome == "mm9") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm9/Mus_musculus.NCBIM37.64.fixed.gtf"
   }else if (genome == "mm10") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm10/gencode.vM3.annotation.gtf"
   }

   retval
  }

  def exonLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/annotations/gencode.v19.annotation.exons.bed"
   }else if(genome == "mm9") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm9/Mus_musculus.NCBIM37.64.fixed.exons.bed"
   }else if(genome == "mm10") {
      retval = "/thunderstorm/users/mlovci/Genomes/mm10/annotations/gencode.vM3.annotation.exons.bed"
   }else if(genome == "ce10") {
      retval = "/thunderstorm/users/mlovci/Genomes/ce10/ce10.exons.BED"
   }

   retval
  }

def gcLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/annotations/gencode.v19.gc.txt"
   }
   retval
  }

def snpDbLocation(genome: String) : String = {
  //Returns star genome Location for TSCC, could eventually be factored out into conf file

   var retval = "none"
   if (genome == "hg19") {
      retval = "/thunderstorm/users/mlovci/Genomes/hg19/annotations/snp137.txt.gz"
   }
   retval
  }


}
