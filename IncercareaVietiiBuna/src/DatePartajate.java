
public class DatePartajate {
	private static DatePartajate instance;

	private  String pathOrar;
	
	//Variabile OrarStudent
	private String sectie;
	private String sAn;
	private String sGrupa;
	private String sSubgrupa;
	private boolean cerereOrar;
	
	//Variabile OrarProfesor
	private String sZiua;
	private String sOraStart;
	private String sOraEnd;
	private String sNumeProfesor;
	
	//Variabile OrarDisciplina
	private String sNumeDisciplina;

	
	//OrarStudent date partajate
	public String getSectie() {
		return sectie;
	}

	public void setSectie(String sectie) {
		this.sectie = sectie;
	}
	
	public String getsGrupa() {
		return sGrupa;
	}

	public void setsGrupa(String sGrupa) {
		this.sGrupa = sGrupa;
	}
	
	public String getsSubgrupa() {
		return sSubgrupa;
	}

	public void setsSubgrupa(String sSubgrupa) {
		this.sSubgrupa = sSubgrupa;
	}
	
	public String getsAn() {
		return sAn;
	}

	public void setsAn(String sAn) {
		this.sAn = sAn;
	}
	
	//cerereOrar e nevoie in a sti daca se cere orarul pe grupa sau subgrupa
	public boolean getcerereOrar() {
		return cerereOrar;
	}

	public void setcerereOrar(boolean cerereOrar) {
		this.cerereOrar = cerereOrar;
	}
	///Terminare OrarStudent date partajate

	//Orar Profesor
	public String getZiua() {
		return sZiua;
	}

	public void setZiua(String sZiua) {
		this.sZiua = sZiua;
	}
	
	public String getOraStart() {
		return sOraStart;
	}

	public void setOraStart(String sOraStart) {
		this.sOraStart = sOraStart;
	}
	
	public String getOraEnd() {
		return sOraEnd;
	}

	public void setOraEnd(String sOraEnd) {
		this.sOraEnd = sOraEnd;
	}
	
	public String getNumeProfesor() {
		return sNumeProfesor;
	}

	public void setNumeProfesor(String sNumeProfesor) {
		this.sNumeProfesor = sNumeProfesor;
	}
	
	//Orar Disciplina
	public String getNumeDisciplina() {
		return sNumeDisciplina;
	}

	public void setNumeDisciplina(String sNumeDisciplina) {
		this.sNumeDisciplina = sNumeDisciplina;
	}

	//Paths
	public  String getPathOrar() {
		return pathOrar;
	}

	public  void setPathOrar(String pathOrar) {
		this.pathOrar = pathOrar;
	}

	private DatePartajate() {
		super();

	}

	public static synchronized DatePartajate getInstance() {
		if (instance == null) {
			instance = new DatePartajate();
		}
		return instance;
	}
}