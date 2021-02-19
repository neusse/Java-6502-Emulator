public class CPU {
	public String[] opcodes;
	
	public byte flags = 0x00;
	//C,Z,I,D,B,U,V,N
	//Carry, Zero, Interrupt Disable, Decimal, Break, Unused, Overflow, Negative
	
	public byte a = 0x00;
	public byte x = 0x00;
	public byte y = 0x00;
	public byte stackPointer = 0x00;
	public short programCounter = 0x0000;
	
	public short addressAbsolute = 0x0000;
	public short addressRelative = 0x0000;
	public byte opcode = 0x00;
	public int cycles = 0;
	
	int additionalCycles = 0;
	
	public Instruction[] lookup = new Instruction[0xFF];
	
	public CPU() {
		reset();
		
		//ADC
		lookup[0x69] = new Instruction("ADC","IMM",2);
		lookup[0x65] = new Instruction("ADC","ZPP",3);
		lookup[0x75] = new Instruction("ADC","ZPX",4);
		lookup[0x6D] = new Instruction("ADC","ABS",4);
		lookup[0x7D] = new Instruction("ADC","ABX",4);
		lookup[0x79] = new Instruction("ADC","ABY",4);
		lookup[0x61] = new Instruction("ADC","IZX",6);
		lookup[0x71] = new Instruction("ADC","IZY",5);
		
		lookup[0x29] = new Instruction("AND","IMM",2);
		lookup[0x25] = new Instruction("AND","ZPP",3);
		lookup[0x35] = new Instruction("AND","ZPX",4);
		lookup[0x2D] = new Instruction("AND","ABS",4);
		lookup[0x3D] = new Instruction("AND","ABX",4);
		lookup[0x39] = new Instruction("AND","ABY",4);
		lookup[0x21] = new Instruction("AND","IZX",6);
		lookup[0x31] = new Instruction("AND","IZY",5);
		
		lookup[0x0A] = new Instruction("ASL","IMP",2);
		lookup[0x06] = new Instruction("ASL","ZPP",5);
		lookup[0x16] = new Instruction("ASL","ZPX",6);
		lookup[0x0E] = new Instruction("ASL","ABS",6);
		lookup[0x1E] = new Instruction("ASL","ABX",7);
		
		lookup[0x90] = new Instruction("BCC","REL",2);
		
		lookup[0xB0] = new Instruction("BCS","REL",2);
		
		lookup[0xF0] = new Instruction("BEQ","REL",2);
		
		lookup[0x24] = new Instruction("BIT","ZP0",3);
		lookup[0x2C] = new Instruction("BIT","ABS",4);
		
		lookup[0x30] = new Instruction("BMI","REL",2);
		
		lookup[0xD0] = new Instruction("BNE","REL",2);
		
		lookup[0x10] = new Instruction("BPL","REL",2);
		
		lookup[0x00] = new Instruction("BRK","IMP",2);
		
		lookup[0x50] = new Instruction("BVC","REL",2);
		
		lookup[0x70] = new Instruction("BVS","REL",2);
		
		lookup[0x18] = new Instruction("CLC","IMP",2);
		
		lookup[0xD8] = new Instruction("CLD","IMP",2);
		
		lookup[0x58] = new Instruction("CLI","IMP",2);
		
		lookup[0xB8] = new Instruction("CLV","IMP",2);
		
		lookup[0xC9] = new Instruction("CMP","IMM",2);
		lookup[0xC5] = new Instruction("CMP","ZPP",3);
		lookup[0xD5] = new Instruction("CMP","ZPX",4);
		lookup[0xCD] = new Instruction("CMP","ABS",4);
		lookup[0xDD] = new Instruction("CMP","ABX",4);
		lookup[0xD9] = new Instruction("CMP","ABY",4);
		lookup[0xC1] = new Instruction("CMP","IZX",6);
		lookup[0xD1] = new Instruction("CMP","IZY",5);
		
		lookup[0xE0] = new Instruction("CPX","IMM",2);
		lookup[0xE4] = new Instruction("CPX","ZPP",3);
		lookup[0xEC] = new Instruction("CPX","ABS",4);
		
		lookup[0xC0] = new Instruction("CPX","IMM",2);
		lookup[0xC4] = new Instruction("CPX","ZPP",3);
		lookup[0xCC] = new Instruction("CPX","ABS",4);
		
		lookup[0xC6] = new Instruction("CPX","ZPP",5);
		lookup[0xD6] = new Instruction("CPX","ZPX",6);
		lookup[0xCE] = new Instruction("CPX","ABS",6);
		lookup[0xDE] = new Instruction("CPX","ABX",7);
		
		lookup[0xCA] = new Instruction("DEX","IMP",2);
		
		lookup[0x88] = new Instruction("DEY","IMP",2);
		
		lookup[0x49] = new Instruction("EOR","IMM",2);
		lookup[0x45] = new Instruction("EOR","ZPP",3);
		lookup[0x55] = new Instruction("EOR","ZPX",4);
		lookup[0x4D] = new Instruction("EOR","ABS",4);
		lookup[0x5D] = new Instruction("EOR","ABX",4);
		lookup[0x59] = new Instruction("EOR","ABY",4);
		lookup[0x41] = new Instruction("EOR","IZX",6);
		lookup[0x51] = new Instruction("EOR","IZY",5);
		
		lookup[0xE6] = new Instruction("INC","IMM",5);
		lookup[0xF6] = new Instruction("INC","ZPP",6);
		lookup[0xEE] = new Instruction("INC","ZPX",6);
		lookup[0xFE] = new Instruction("INC","ABS",7);
		
		lookup[0xE8] = new Instruction("INX","IMP",2);
		
		lookup[0xC8] = new Instruction("INY","IMP",2);
		
		lookup[0x4C] = new Instruction("JMP","ABS",3);
		lookup[0x6C] = new Instruction("JMP","IND",5);
		
		lookup[0x20] = new Instruction("JSR","ABS",6);
		
		lookup[0xA9] = new Instruction("LDA","IMM",2);
		lookup[0xA5] = new Instruction("LDA","ZPP",3);
		lookup[0xB5] = new Instruction("LDA","ZPX",4);
		lookup[0xAD] = new Instruction("LDA","ABS",4);
		lookup[0xBD] = new Instruction("LDA","ABX",4);
		lookup[0xB9] = new Instruction("LDA","ABY",4);
		lookup[0xA1] = new Instruction("LDA","IZX",6);
		lookup[0xB1] = new Instruction("LDA","IZY",5);
		
		lookup[0xA2] = new Instruction("LDX","IMM",2);
		lookup[0xA6] = new Instruction("LDX","ZPP",3);
		lookup[0xB6] = new Instruction("LDX","ZPY",4);
		lookup[0xAE] = new Instruction("LDX","ABS",4);
		lookup[0xBE] = new Instruction("LDX","ABY",4);
		
		lookup[0xA0] = new Instruction("LDY","IMM",2);
		lookup[0xA4] = new Instruction("LDY","ZPP",3);
		lookup[0xB4] = new Instruction("LDY","ZPY",4);
		lookup[0xAC] = new Instruction("LDY","ABS",4);
		lookup[0xBC] = new Instruction("LDY","ABY",4);
		
		lookup[0x4A] = new Instruction("LSR","IMM",2);
		lookup[0x46] = new Instruction("LSR","ZPP",5);
		lookup[0x56] = new Instruction("LSR","ZPX",6);
		lookup[0x4E] = new Instruction("LSR","ABS",6);
		lookup[0x5E] = new Instruction("LSR","ABX",7);
		
		lookup[0xEA] = new Instruction("NOP","IMP",2);
		
		lookup[0x09] = new Instruction("ORA","IMM",2);
		lookup[0x05] = new Instruction("ORA","ZPP",3);
		lookup[0x15] = new Instruction("ORA","ZPX",4);
		lookup[0x0D] = new Instruction("ORA","ABS",4);
		lookup[0x1D] = new Instruction("ORA","ABX",4);
		lookup[0x19] = new Instruction("ORA","ABY",4);
		lookup[0x01] = new Instruction("ORA","IZX",6);
		lookup[0x11] = new Instruction("ORA","IZY",5);
		
		lookup[0x48] = new Instruction("PHA","IMP",3);
		
		lookup[0x08] = new Instruction("PHP","IMP",3);
		
		lookup[0x68] = new Instruction("PLA","IMP",4);
		
		lookup[0x28] = new Instruction("PLP","IMP",4);
		
		lookup[0x2A] = new Instruction("ROR","IMM",2);
		lookup[0x26] = new Instruction("ROR","ZPP",5);
		lookup[0x36] = new Instruction("ROR","ZPX",6);
		lookup[0x2E] = new Instruction("ROR","ABS",6);
		lookup[0x3E] = new Instruction("ROR","ABX",7);
		
		lookup[0x6A] = new Instruction("ROL","IMM",2);
		lookup[0x66] = new Instruction("ROL","ZPP",5);
		lookup[0x76] = new Instruction("ROL","ZPX",6);
		lookup[0x6E] = new Instruction("ROL","ABS",6);
		lookup[0x7E] = new Instruction("ROL","ABX",7);
		
		lookup[0x40] = new Instruction("RTI","IMP",6);
		
		lookup[0x60] = new Instruction("RTS","IMP",6);
		
		lookup[0xE9] = new Instruction("SBC","IMM",2);
		lookup[0xE5] = new Instruction("SBC","ZPP",3);
		lookup[0xF5] = new Instruction("SBC","ZPX",4);
		lookup[0xED] = new Instruction("SBC","ABS",4);
		lookup[0xFD] = new Instruction("SBC","ABX",4);
		lookup[0xF9] = new Instruction("SBC","ABY",4);
		lookup[0xE1] = new Instruction("SBC","IZX",6);
		lookup[0xF1] = new Instruction("SBC","IZY",5);
		
		lookup[0x38] = new Instruction("SEC","IMP",2);
		
		lookup[0xF8] = new Instruction("SED","IMP",2);
		
		lookup[0x78] = new Instruction("SEI","IMP",2);
		
		lookup[0x85] = new Instruction("STA","ZPP",3);
		lookup[0x95] = new Instruction("STA","ZPX",4);
		lookup[0x8D] = new Instruction("STA","ABS",4);
		lookup[0x9D] = new Instruction("STA","ABX",5);
		lookup[0x99] = new Instruction("STA","ABY",5);
		lookup[0x81] = new Instruction("STA","IZX",6);
		lookup[0x91] = new Instruction("STA","IZY",6);
		
		lookup[0x86] = new Instruction("STX","ZPP",3);
		lookup[0x96] = new Instruction("STX","ZPX",4);
		lookup[0x8E] = new Instruction("STX","ABS",4);
		
		lookup[0x84] = new Instruction("STY","ZPP",3);
		lookup[0x94] = new Instruction("STY","ZPX",4);
		lookup[0x8C] = new Instruction("STY","ABS",4);
		
		lookup[0xAA] = new Instruction("TAX","IMP",2);
		
		lookup[0xA8] = new Instruction("TAY","IMP",2);
		
		lookup[0xBA] = new Instruction("TSX","IMP",2);
		
		lookup[0x8A] = new Instruction("TXA","IMP",2);
		
		lookup[0x9A] = new Instruction("TXS","IMP",2);
		
		lookup[0x98] = new Instruction("TYA","IMP",2);
	}
	
	void setFlag(char flag, boolean condition) {
		flag = Character.toUpperCase(flag);
		switch (flag) {
		case 'C':
			flags = setBit(flags,7,condition);
			break;
		case 'Z':
			flags = setBit(flags,6,condition);
			break;
		case 'I':
			flags = setBit(flags,5,condition);
			break;
		case 'D':
			flags = setBit(flags,4,condition);
			break;
		case 'B':
			flags = setBit(flags,3,condition);
			break;
		case 'U':
			flags = setBit(flags,2,condition);
			break;
		case 'V':
			flags = setBit(flags,1,condition);
			break;
		case 'N':
			flags = setBit(flags,0,condition);
			break;
		}
	}
	
	boolean getFlag(char flag) {
		flag = Character.toUpperCase(flag);
		switch (flag) {
		case 'C':
			return ((flags&0b10000000) == 0b10000000);
		case 'Z':
			return ((flags&0b01000000) == 0b01000000);
		case 'I':
			return ((flags&0b00100000) == 0b00100000);
		case 'D':
			return ((flags&0b00010000) == 0b00010000);
		case 'B':
			return ((flags&0b00001000) == 0b00001000);
		case 'U':
			return ((flags&0b00000100) == 0b00000100);
		case 'V':
			return ((flags&0b00000010) == 0b00000010);
		case 'N':
			return ((flags&0b00000001) == 0b00000001);
		}
		System.out.println("Something has gone wrong in getFlag!");
		return false;
	}
	
	public static byte setBit(byte b, int bit, boolean value) {
		if (value) {
			b |= (byte)(0x00+Math.pow(2, bit));
		} else {
			b &= (byte)(0xFF-Math.pow(2, bit));
		}
		return b;
	}
	
	void clock() {
		if (cycles == 0) {
			additionalCycles = 0;
			opcode = Bus.read(programCounter);
			programCounter++;
			
			cycles = lookup[Byte.toUnsignedInt(opcode)].cycles;

			try {
				this.getClass().getMethod(lookup[Byte.toUnsignedInt(opcode)].addressMode).invoke(this);
				this.getClass().getMethod(lookup[Byte.toUnsignedInt(opcode)].opcode).invoke(this);
			} catch (Exception e) {e.printStackTrace();}
		}
		
		cycles--;
	}
	
	//Input Signal Handlers
	void reset() {
		a = 0;
		x = 0;
		y = 0;
		stackPointer = (byte)0xFD;
		flags = (byte)(0x00 | (getFlag('U') ? 0b00000100 : 0));
		
		addressAbsolute = (short)(0xFFFC);
		
		byte lo = Bus.read(addressAbsolute);
		byte hi = Bus.read((short)(addressAbsolute+1));
		programCounter = (short)(Byte.toUnsignedInt(lo)+256*Byte.toUnsignedInt(hi));
		
		addressRelative = 0;
		addressAbsolute = 0;
		fetched = 0;
		
		cycles = 8;
	}
	
	void irq() {
		if (!getFlag('I')) {
			Bus.write((short)(0x0100+stackPointer), (byte)((programCounter>>8)&0x00FF));
			stackPointer--;
			Bus.write((short)(0x0100+stackPointer), (byte)(programCounter&0x00FF));
			stackPointer--;
			
			setFlag('B',false);
			setFlag('U',false);
			setFlag('I',true);
			
			Bus.write((short)(0x0100+stackPointer), flags);
			stackPointer--;
			
			addressAbsolute = (short)0xFFFE;
			byte lo = Bus.read(addressAbsolute);
			byte hi = Bus.read((short)(addressAbsolute+1));
			programCounter = (short)(Byte.toUnsignedInt(lo)+256*Byte.toUnsignedInt(hi));
			
			cycles = 7;
		}
	}
	
	void nmi() {
		Bus.write((short)(0x0100+stackPointer), (byte)((programCounter>>8)&0x00FF));
		stackPointer--;
		Bus.write((short)(0x0100+stackPointer), (byte)(programCounter&0x00FF));
		stackPointer--;
		
		setFlag('B',false);
		setFlag('U',false);
		setFlag('I',true);
		
		Bus.write((short)(0x0100+stackPointer), flags);
		stackPointer--;
		
		addressAbsolute = (short)0xFFFA;
		byte lo = Bus.read(addressAbsolute);
		byte hi = Bus.read((short)(addressAbsolute+1));
		programCounter = (short)(Byte.toUnsignedInt(lo)+256*Byte.toUnsignedInt(hi));
		
		cycles = 7;
	}
	
	//Data Getter
	byte fetched = 0x00;
	byte fetch() {
		if (!(lookup[Byte.toUnsignedInt(opcode)].addressMode.equals("IMP")))
			fetched = Bus.read(addressAbsolute);
		return fetched;
	}
	
	//Addressing Modes
	public void IMP() {
		fetched = a;
	}
	
	public void IMM() {
		addressAbsolute = programCounter++;
	}
	
	public void ZP0() {
		addressAbsolute = Bus.read(programCounter);
		programCounter++;
		addressAbsolute &= 0x00FF;
	}
	
	public void ZPX() {
		addressAbsolute = (short)(Bus.read(programCounter)+x);
		programCounter++;
		addressAbsolute &= 0x00FF;
	}
	
	public void ZPY() {
		addressAbsolute = (short)(Bus.read(programCounter)+y);
		programCounter++;
		addressAbsolute &= 0x00FF;
	}
	
	public void REL() {
		addressRelative = Bus.read(programCounter);
		programCounter++;
		if ((addressRelative & 0x80)==0x80)
			addressRelative |= 0xFF00;
	}
	
	public void ABS() {
		byte lo = Bus.read(programCounter);
		programCounter++;
		byte hi = Bus.read(programCounter);
		programCounter++;
		
		addressAbsolute = (short)(Byte.toUnsignedInt(lo)+256*Byte.toUnsignedInt(hi));
	}
	
	public void ABX() {
		byte lo = Bus.read(programCounter);
		programCounter++;
		byte hi = Bus.read(programCounter);
		programCounter++;
		
		addressAbsolute = (short)(Byte.toUnsignedInt(lo)+256*Byte.toUnsignedInt(hi)+x);
		
		if ((addressAbsolute & 0xFF00) != (hi<<8))
			additionalCycles++;
	}
	
	public void ABY() {
		byte lo = Bus.read(programCounter);
		programCounter++;
		byte hi = Bus.read(programCounter);
		programCounter++;
		
		addressAbsolute = (short)(Byte.toUnsignedInt(lo)+256*Byte.toUnsignedInt(hi)+y);
		
		if ((addressAbsolute & 0xFF00) != (hi<<8))
			additionalCycles++;
	}
	
	public void IND() {
		byte lowPointer = Bus.read(programCounter);
		programCounter++;
		byte highPointer = Bus.read(programCounter);
		programCounter++;
		
		short pointer = (short)((highPointer << 8) | lowPointer);
		
		addressAbsolute = (short)(Byte.toUnsignedInt(Bus.read((short)(pointer+1)))*256+Byte.toUnsignedInt(Bus.read(pointer)));
	}
	
	public void IZX() {
		byte t = Bus.read(programCounter);
		programCounter++;
		
		byte lo = Bus.read((short)((t+x)&0x00FF));
		byte hi = Bus.read((short)((t+x+1)&0x00FF));
		
		addressAbsolute = (short)(Byte.toUnsignedInt(lo)+256*Byte.toUnsignedInt(hi));
	}
	
	public void IZY() {
		byte t = Bus.read(programCounter);
		programCounter++;
		
		byte lo = Bus.read((short)(t&0x00FF));
		byte hi = Bus.read((short)((t+1)&0x00FF));
		
		addressAbsolute = (short)(Byte.toUnsignedInt(lo)+256*Byte.toUnsignedInt(hi)+y);
		
		if ((addressAbsolute & 0xFF00) != (hi<<8))
			additionalCycles++;
	}
	
	//INSTRUCTIONS
	public void ADC() {
		fetch();
		short temp = (short)((short)a + (short)fetched + (short)(getFlag('C') ? 1 : 0));
		setFlag('C', temp > 255);
		setFlag('Z', (temp & 0x00FF) == 0);
		setFlag('N', (temp & 0x80) == 0x80);
		setFlag('V', (~((short)a^(short)fetched) & ((short)a^(short)temp) & 0x0080)==0x0080);
		a = (byte)temp;
		additionalCycles++;
	}
	
	public void AND() {
		fetch();
		a &= fetched;
		setFlag('Z', a==0x00);
		setFlag('N', (a & 0x80)==0x80);
		
		additionalCycles++;
	}
		
	public void ASL() {
		fetch();
		short temp = (short)(fetched << 1);
		setFlag('Z', (temp & 0x00FF)==0x00);
		setFlag('C', Short.toUnsignedInt((short)(temp & 0xFF00)) > 0);
		setFlag('N', (temp & 0x80)==0x80);
		
		if (lookup[Byte.toUnsignedInt(opcode)].addressMode.equals("IMP")) {
			a = (byte)(temp & 0x00FF);
		} else {
			Bus.write(addressAbsolute, (byte)(temp & 0x00FF));
		}
	}
		
	public void BCC() {
		if (!getFlag('C')) {
			cycles++;
			addressAbsolute = (short)(programCounter+addressRelative);
			
			if ((addressAbsolute&0xFF00) != (programCounter & 0xFF00))
				cycles++;
				
			programCounter = addressAbsolute;
		}
	}
		
	public void BCS() {
		if (getFlag('C')) {
			cycles++;
			addressAbsolute = (short)(programCounter+addressRelative);
			
			if ((addressAbsolute&0xFF00) != (programCounter & 0xFF00))
				cycles++;
				
			programCounter = addressAbsolute;
		}
	}
		
	public void BEQ() {
		if (getFlag('Z')) {
			cycles++;
			addressAbsolute = (short)(programCounter+addressRelative);
			
			if ((addressAbsolute&0xFF00) != (programCounter & 0xFF00))
				cycles++;
				
			programCounter = addressAbsolute;
		}
	}
		
	public void BIT() {
		fetch();
		short temp = (short)(a&fetched);
		setFlag('Z',(temp&0x00FF)==0x00);
		setFlag('N',(fetched&0x80)==0x80);
		setFlag('V',(fetched&0x40)==0x40);
	}
		
	public void BMI() {
		if (getFlag('N')) {
			cycles++;
			addressAbsolute = (short)(programCounter+addressRelative);
			
			if ((addressAbsolute&0xFF00) != (programCounter & 0xFF00))
				cycles++;
				
			programCounter = addressAbsolute;
		}
	}
		
	public void BNE() {
		if (!getFlag('Z')) {
			cycles++;
			addressAbsolute = (short)(programCounter+addressRelative);
			
			if ((addressAbsolute&0xFF00) != (programCounter & 0xFF00))
				cycles++;
				
			programCounter = addressAbsolute;
		}
	}
		
	public void BPL() {
		if (!getFlag('N')) {
			cycles++;
			addressAbsolute = (short)(programCounter+addressRelative);
			
			if ((addressAbsolute&0xFF00) != (programCounter & 0xFF00))
				cycles++;
				
			programCounter = addressAbsolute;
		}
	}
		
	public void BRK() {
		programCounter++;
		setFlag('I',true);
		Bus.write((short)(0x0100+stackPointer), (byte)((programCounter>>8)&0x00FF));
		stackPointer--;
		Bus.write((short)(0x0100+stackPointer), (byte)(programCounter&0x00FF));
		stackPointer--;
		
		setFlag('B',true);
		Bus.write((short)(0x0100+stackPointer), flags);
		stackPointer--;
		setFlag('B',false);
		
		addressAbsolute = (short)0xFFFE;
		byte lo = Bus.read(addressAbsolute);
		byte hi = Bus.read((short)(addressAbsolute+1));
		programCounter = (short)(Byte.toUnsignedInt(lo)+256*Byte.toUnsignedInt(hi));
	}
		
	public void BVC() {
		if (getFlag('V')) {
			cycles++;
			addressAbsolute = (short)(programCounter+addressRelative);
			
			if ((addressAbsolute&0xFF00) != (programCounter & 0xFF00))
				cycles++;
				
			programCounter = addressAbsolute;
		}
	}
		
	public void BVS() {
		if (!getFlag('V')) {
			cycles++;
			addressAbsolute = (short)(programCounter+addressRelative);
			
			if ((addressAbsolute&0xFF00) != (programCounter & 0xFF00))
				cycles++;
				
			programCounter = addressAbsolute;
		}
	}
		
	public void CLC() {
		setFlag('C',false);
	}
		
	public void CLD() {
		setFlag('D',false);
	}
		
	public void CLI() {
		setFlag('I',false);
	}
		
	public void CLV() {
		setFlag('V',false);
	}
		
	public void CMP() {
		fetch();
		short temp = (short)(Byte.toUnsignedInt(a) - Byte.toUnsignedInt(fetched));
		setFlag('C',Byte.toUnsignedInt(a) >= Byte.toUnsignedInt(fetched));
		setFlag('Z',(temp&0x00FF)==0x0000);
		setFlag('N',(temp&0x0080)==0x0080);
		
		additionalCycles++;
	}
		
	public void CPX() {
		fetch();
		short temp = (short)(Byte.toUnsignedInt(x) - Byte.toUnsignedInt(fetched));
		setFlag('C',Byte.toUnsignedInt(x) >= Byte.toUnsignedInt(fetched));
		setFlag('Z',(temp&0x00FF)==0x0000);
		setFlag('N',(temp&0x0080)==0x0080);
	}
		
	public void CPY() {
		fetch();
		short temp = (short)(Byte.toUnsignedInt(y) - Byte.toUnsignedInt(fetched));
		setFlag('C',Byte.toUnsignedInt(y) >= Byte.toUnsignedInt(fetched));
		setFlag('Z',(temp&0x00FF)==0x0000);
		setFlag('N',(temp&0x0080)==0x0080);
	}
		
	public void DEC() {
		fetch();
		short temp = (short)(fetched-1);
		Bus.write(addressAbsolute, (byte)(temp&0x00FF));
		setFlag('Z',(temp&0x00FF)==0x0000);
		setFlag('N',(temp&0x0080)==0x0080);
	}
		
	public void DEX() {
		x--;
		setFlag('Z',x==0x00);
		setFlag('N',(x&0x80)==0x80);
	}
		
	public void DEY() {
		y--;
		setFlag('Z',y==0x00);
		setFlag('N',(y&0x80)==0x80);
	}
		
	public void EOR() {
		fetch();
		a ^= fetched;
		setFlag('Z', a==0x00);
		setFlag('N', (a & 0x80)==0x80);
		
		additionalCycles++;
	}
		
	public void INC() {
		fetch();
		short temp = (short)(fetched+1);
		Bus.write(addressAbsolute, (byte)(temp&0x00FF));
		setFlag('Z',(temp&0x00FF)==0x0000);
		setFlag('N',(temp&0x0080)==0x0080);
	}
		
	public void INX() {
		x++;
		setFlag('Z',x==0x00);
		setFlag('N',(x&0x80)==0x80);
	}
		
	public void INY() {
		y++;
		setFlag('Z',y==0x00);
		setFlag('N',(y&0x80)==0x80);
	}
		
	public void JMP() {
		programCounter = addressAbsolute;
	}
		
	public void JSR() {
		programCounter--;
		
		Bus.write((short)(0x0100+stackPointer), (byte)((programCounter>>8)&0x00FF));
		stackPointer--;
		Bus.write((short)(0x0100+stackPointer), (byte)(programCounter&0x00FF));
		stackPointer--;
		
		programCounter = addressAbsolute;
	}
		
	public void LDA() {
		fetch();
		a = fetched;
		setFlag('Z',a==0x00);
		setFlag('N',(a&0x80)==0x80);
		additionalCycles++;
	}
		
	public void LDX() {
		fetch();
		x = fetched;
		setFlag('Z',x==0x00);
		setFlag('N',(x&0x80)==0x80);
		additionalCycles++;
	}
		
	public void LDY() {
		fetch();
		y = fetched;
		setFlag('Z',y==0x00);
		setFlag('N',(y&0x80)==0x80);
		additionalCycles++;
	}
		
	public void LSR() {
		fetch();
		setFlag('C',(fetched&0x0001)==0x0001);
		short temp = (short)(fetched >> 1);
		setFlag('Z',(temp&0x00FF)==0x0000);
		setFlag('N',(temp&0x0080)==0x0080);
		if (lookup[Byte.toUnsignedInt(opcode)].addressMode.equals("IMP")) {
			a = (byte)(temp&0x00FF);
		} else {
			Bus.write(addressAbsolute, (byte)(temp&0x00FF));
		}
	}
		
	public void NOP() {
		additionalCycles++;
	}
		
	public void ORA() {
		fetch();
		a |= fetched;
		setFlag('Z', a==0x00);
		setFlag('N', (a & 0x80)==0x80);
		
		additionalCycles++;
	}
		
	public void PHA() {
		Bus.write((short)(0x0100+stackPointer), a);
		stackPointer--;
	}
		
	public void PHP() {
		Bus.write((short)(0x0100+stackPointer), (byte)(flags|0b00001100));
		setFlag('B',false);
		setFlag('U',false);
		stackPointer--;
	}
		
	public void PLA() {
		stackPointer++;
		a = Bus.read((short)(0x0100+stackPointer));
		setFlag('Z', a == 0);
		setFlag('N', (a & 0x80) == 0x80);
	}
		
	public void PLP() {
		stackPointer++;
		flags = Bus.read((short)(0x0100+stackPointer));
		setFlag('U', true);
	}
		
	public void ROL() {
		fetch();
		short temp = (short)((fetched<<1) | (getFlag('C') ? 1 : 0));
		setFlag('C',(temp&0xFF00) == 0xFF00);
		setFlag('Z',(temp&0x00FF) == 0x0000);
		setFlag('N',(temp&0x0080) == 0x0080);
		if (lookup[Byte.toUnsignedInt(opcode)].addressMode.equals("IMP")) {
			a = (byte)(temp&0x00FF);
		} else {
			Bus.write(addressAbsolute, (byte)(temp&0x00FF));
		}
	}
		
	public void ROR() {
		fetch();
		short temp = (short)((fetched>>1) | (getFlag('C') ? 0x80 : 0));
		setFlag('C',(fetched&0x01) == 0x01);
		setFlag('Z',(temp&0x00FF) == 0x0000);
		setFlag('N',(temp&0x0080) == 0x0080);
		if (lookup[Byte.toUnsignedInt(opcode)].addressMode.equals("IMP")) {
			a = (byte)(temp&0x00FF);
		} else {
			Bus.write(addressAbsolute, (byte)(temp&0x00FF));
		}
	}
		
	public void RTI() {
		stackPointer++;
		flags = Bus.read((short)(0x0100+stackPointer));
		flags = (byte)(flags & ~(getFlag('B') ? 0b00000100 : 0));
		flags = (byte)(flags & ~(getFlag('U') ? 0b00000100 : 0));
		
		stackPointer++;
		programCounter = Bus.read((short)(0x0100+stackPointer));
		stackPointer++;
		programCounter |= Bus.read((short)(0x0100+stackPointer)) << 8;
	}
		
	public void RTS() {
		stackPointer++;
		programCounter = Bus.read((short)(0x0100+stackPointer));
		stackPointer++;
		programCounter |= Bus.read((short)(0x0100+stackPointer)) << 8;
		
		programCounter++;
	}
		
	public void SBC() {
		fetch();
		short value = (short)((short)fetched ^ 0x00FF);
		short temp = (short)((short)a + value + (short)(getFlag('C') ? 1 : 0));
		setFlag('C', temp > 255);
		setFlag('Z', (temp & 0x00FF) == 0);
		setFlag('N', (temp & 0x80) == 0x80);
		setFlag('V', (~((short)a^(short)fetched) & ((short)a^(short)temp) & 0x0080)==0x0080);
		a = (byte)temp;
		additionalCycles++;
	}
		
	public void SEC() {
		setFlag('C',true);
	}
		
	public void SED() {
		setFlag('D',true);
	}
		
	public void SEI() {
		setFlag('I',true);
	}
		
	public void STA() {
		Bus.write(addressAbsolute, a);
	}
		
	public void STX() {
		Bus.write(addressAbsolute, x);
	}
		
	public void STY() {
		Bus.write(addressAbsolute, y);
	}
		
	public void TAX() {
		x = a;
		setFlag('Z',x==0x00);
		setFlag('N',(x&0x80)==0x80);
	}
		
	public void TAY() {
		y = a;
		setFlag('Z',y==0x00);
		setFlag('N',(y&0x80)==0x80);
	}
		
	public void TSX() {
		x = stackPointer;
		setFlag('Z',x==0x00);
		setFlag('N',(x&0x80)==0x80);
	}
		
	public void TXA() {
		a = x;
		setFlag('Z',a==0x00);
		setFlag('N',(a&0x80)==0x80);
	}
		
	public void TXS() {
		stackPointer = x;
	}
		
	public void TYA() {
		a = y;
		setFlag('Z',a==0x00);
		setFlag('N',(a&0x80)==0x80);
	}
	
	public void XXX() {
		System.out.println("Illegal Opcode!");
	}
}