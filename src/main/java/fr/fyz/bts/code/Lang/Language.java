package fr.fyz.bts.code.Lang;

public class Language {

    private final int id;
    private final   String name;


    public Language(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
