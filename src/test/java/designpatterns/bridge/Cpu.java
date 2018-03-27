package designpatterns.bridge;

public class Cpu {
	interface CpuAbility {
		String abilityCpu();
	}

	class AdmCpu implements CpuAbility {
		@Override
		public String abilityCpu() {
			return "normal";
		}

	}

	class IntelCpu implements CpuAbility {
		@Override
		public String abilityCpu() {
			return "awsome";
		}

	}

	abstract class AbstractComputer {
		CpuAbility cpuAbility;

		public AbstractComputer(CpuAbility cpuAbility) {
			this.cpuAbility = cpuAbility;
		}

		public abstract void checkPcAbility();
	}

	class Lenevo extends AbstractComputer {

		public Lenevo(CpuAbility cpuAbility) {
			super(cpuAbility);
		}

		@Override
		public void checkPcAbility() {
			System.out.println(cpuAbility.abilityCpu());
		}

	}

	class Hp extends AbstractComputer {

		public Hp(CpuAbility cpuAbility) {
			super(cpuAbility);
		}

		@Override
		public void checkPcAbility() {
			System.out.println(cpuAbility.abilityCpu());
		}

	}

	public static void main(String[] args) {
		CpuAbility intel = new Cpu().new IntelCpu();
		CpuAbility adm = new Cpu().new AdmCpu();
		AbstractComputer com = new Cpu().new Lenevo(adm);// intel
		com.checkPcAbility();
	}
}
