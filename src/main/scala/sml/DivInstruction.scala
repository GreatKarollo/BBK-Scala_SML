package sml

/**
  *   1. function obtains @params from registers: opt1 and opt2
  *   2. performs DIVISION on  both params
  *   3. writes result to register
  */
class DivInstruction(label: String, op: String, val result: Int, val opt1: Int, val opt2: Int) extends Instruction (label, op) {

  override def execute(m: Machine): Unit = {
    val in1 = m.regs(opt1)
    val in2 = m.regs(opt2)
    m.regs(result) = in1/in2
  }

  override def toString(): String = {
    super.toString + " " + opt1 + " ÷ " + opt2 + " to " + result + "\n"
  }

  object DivInstruction {
    def apply(label: String, result: Int, op1: Int, op2: Int) =
      new DivInstruction(label, "div", result, op1, op2)
  }

}
