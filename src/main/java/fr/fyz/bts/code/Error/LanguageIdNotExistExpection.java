package fr.fyz.bts.code.Error;

public class LanguageIdNotExistExpection extends Exception {

    private static final long serialVersionUID = 1L;

	public LanguageIdNotExistExpection(int id,String url){
        super("Language_ID: "+id+" didn't exist ! Please cheak "+url+"/languages/all to see all supported languages list.");
    }

}
