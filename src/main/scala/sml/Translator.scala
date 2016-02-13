package sml

import scala.collection.mutable.ListBuffer

/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
class Translator(fileName: String) {
  private final val ADD = "add"
  private final val LIN = "lin"

  // word + line is the part of the current line that's not yet processed
  // word has no whitespace
  // If word and line are not empty, line begins with whitespace

  /**
    * translate the small program in the file into lab (the labels) and prog (the program)
    */
  def readAndTranslate(m: Machine): Machine = {
    val labels = m.labels
    var program = m.prog
    import scala.io.Source
    val lines = Source.fromFile(fileName).getLines
    for (line <- lines) {
      val fields = line.split(" ")
      if (fields.length > 0) {

        try {
          val instructionClass = Class.forName("sml." + fields(1).capitalize + "Instruction")
          val runtimeUniverse = scala.reflect.runtime.universe
          val runtimeMirror = runtimeUniverse.runtimeMirror(getClass.getClassLoader)
          val classSymbol = runtimeMirror.classSymbol(instructionClass)
          val classMirror = runtimeMirror.reflectClass(classSymbol)
          val constructorSymbol =  classSymbol.primaryConstructor.asMethod
          val constructorInstruction = classMirror.reflectConstructor(constructorSymbol).symbol.asMethod
          val constructorMirror = classMirror.reflectConstructor(constructorInstruction)
          val constructorParams = constructorInstruction.paramLists
          var instructionParams = new ListBuffer[Any]()
          var counter = 0
          for (paramsList <- constructorParams) {
            for (param <- paramsList) {
              param.info.toString match {
                case "String" => instructionParams.append(fields(counter))
                case "Int" => instructionParams.append(fields(counter).toInt)
                case x => {
                  println("Invalid param type for instruction: " + x)
                  throw new Exception()
                }
              }
              counter += 1
            }
          }



        labels.add(fields(0))


          val temporaryInstruction = constructorMirror.apply(instructionParams: _*).asInstanceOf[sml. Instruction]
          program = program :+ temporaryInstruction
        } catch {
          case caseNotFoundException: java.lang.ClassNotFoundException => println("Illegal Instruction " + fields(1) + ", not implementing this Instruction")
        }
      }
    }

        /* fields(1) match {
          case ADD =>
            program = program :+ AddInstruction(fields(0), fields(2).toInt, fields(3).toInt, fields(4).toInt)
          case LIN =>
            program = program :+ LinInstruction(fields(0), fields(2).toInt, fields(3).toInt)
          case x =>
            println(s"Unknown instruction $x")
        }
      }
    }*/


    new Machine(labels, program)
  }
}

object Translator {

  private val directory: String = "src/"

  def apply(file: String) =
    new Translator(directory + file)
}