package example
import scala.io.Source._
import scala.util.matching.Regex
import scala.collection.immutable.HashMap
import scala.collection.parallel.CollectionConverters._
import java.io.File
object Hello extends App {
  val lettersOnly = "([a-z]+)".r
  val dictExtract = """\s"([A-Za-z]+)"\:\s1\,""".r
  val someFile = new File("book")
  val fileSizeMB = someFile.length/1024/1024
  val bookLinesIter = fromFile("book").getLines
 
  //prepare the dictionary
  val dictionaryLines = fromFile("NameSurname.json").getLines
  val namesIter = dictionaryLines.flatMap { line =>
    line match {
      case dictExtract(name) => List(name.toLowerCase)
      case _ => List()
    }
  }

  print(f"Loading the text (${fileSizeMB}%d MB) in memory...")
  var currentTime = System.currentTimeMillis()
  val bookLines = bookLinesIter.toList
  var endTime = System.currentTimeMillis()
  println(f"Loading the text (${fileSizeMB}%d MB) in memory took ${(endTime-currentTime)/1000f}%.4f seconds")
 
  print(f"Cleaning and transforming the text")
   currentTime = System.currentTimeMillis()
  val words = bookLines.par.flatMap { line => 
    //prepare list of lowercase words
    val wordsRaw = line.split(" ").map(_.trim.toLowerCase).filter { w =>
      w match {
        case lettersOnly(_) => true
        case _ => false
      } 
    }
    wordsRaw
  }
  endTime = System.currentTimeMillis()
  println(f"Cleaning and transforming the text took ${(endTime-currentTime)/1000f}%.4f seconds")

  println("Loading the dictionary in memory...")
  currentTime = System.currentTimeMillis()
  val dict = namesIter.toList
  endTime = System.currentTimeMillis()
  println(f"Loading the dictionary in memory took ${(endTime-currentTime)/1000f}%.4f seconds")
  
  println("Transforming dictionary to hashmap")
  currentTime = System.currentTimeMillis()
  val dictHM = (dict.map {
    case name => name -> 1
  }).toMap
  endTime = System.currentTimeMillis()
  println(f"Transforming dictionary to hashmap took ${(endTime-currentTime)/1000f}%.4f seconds")

  println("Locating names in text")
  currentTime = System.currentTimeMillis()
  val names = words.filter( word => dictHM.contains(word) )
  endTime = System.currentTimeMillis()
  println(f"Locating names in text took ${(endTime-currentTime)/1000f}%.4f seconds")

  val namesCount = names.length
  val wordsCount = words.length
  println(f"Found ${namesCount}%d names in ${wordsCount} words long text.")

}

