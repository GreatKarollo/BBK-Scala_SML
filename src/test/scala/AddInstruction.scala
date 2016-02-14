import org.scalatest.FunSuite

/**
  * Created by karolsudol on 14/02/2016.
  */
class AddInstruction extends FunSuite{

  test("toString") {
    val addInstruction = new AddInstruction("L1", "add",4, 2, 2)
    assert(addInstruction.toString == "L1: add " + 1.toString + " + " + 1.toString + " to " + 2.toString)
  }
  test("apply") {
    assert(true)
  }
}
