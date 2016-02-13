package sml

/**
  * function evaluates  @param opRegister, if opRegister != 0 instruction is executed with label: dest
  */
class BnzInstruction(label: String, op: String, val opRegister: Int, val dest: String)extends Instruction(label, op){

  override def execute(m: Machine): Unit = {
    val in = m.regs(opRegister)
    val destLine = m.labels.labels.indexOf(dest)
      if (in != 0) m.execute(destLine)
    }

    override def toString(): String = {
      super.toString + " if " + opRegister + " ≠ 0 go to " + dest + "\n"
    }
}

object BnzInstruction {
  def apply(label: String, opRegister: Int, dest: String) =
  new BnzInstruction(label,"BNZ", opRegister, dest)
}

