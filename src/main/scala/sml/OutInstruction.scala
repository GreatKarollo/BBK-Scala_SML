package sml

/**
  * output: registerOut.
  */
class OutInstruction (label: String, op: String, val registerOut: Int) extends Instruction(label, op){


  override def execute(m: Machine) {
    println(m.regs(registerOut))
  }

  override def toString(): String = {
    super.toString + " print value of " + registerOut + "\n"
  }
}

object OutInstruction {
  def apply(label: String, registerOut: Int) =
    new OutInstruction(label, "out", registerOut)
}


