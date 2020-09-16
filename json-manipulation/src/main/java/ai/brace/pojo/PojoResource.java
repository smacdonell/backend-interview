package ai.brace.pojo;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * POJO representation of the JSON in resources
 */
public class PojoResource implements Serializable {
    protected String version;

    protected String uuid;

    protected String lastModified;

    protected String title;

    protected String author;

    protected String translator;

    protected String releaseDate;

    protected String language;

    protected List<TextArray> textArray;

    public Date getLastModifiedAsDate() {
        try {
            return new Date(Integer.parseInt(getLastModified()));
        } catch(NumberFormatException e) {
            System.err.println(e);
        }

        return null;
    }

    public void setVersion(String version){
        this.version = version;
    }

    public String getVersion(){
        return this.version;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getUuid(){
        return this.uuid;
    }

    public void setLastModified(String lastModified){
        this.lastModified = lastModified;
    }

    public String getLastModified(){
        return this.lastModified;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setTranslator(String translator){
        this.translator = translator;
    }

    public String getTranslator(){
        return this.translator;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public String getLanguage(){
        return this.language;
    }

    public void setTextArray(List<TextArray> textArray) {
        this.textArray = textArray;
    }

    public List<TextArray> getTextArray() {
        if(this.textArray == null) {
            this.textArray = new ArrayList<>();
        }

        return this.textArray;
    }

    public class TextArray implements Serializable {
        protected int id;

        protected String textdata;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setTextdata(String textdata) {
            this.textdata = textdata;
        }

        public String getTextdata() {
            return this.textdata;
        }
    }

    /**
     * Copies values from another PojoResource using reflection to get the available Fields and getter/setter methods.
     *
     * @param resource
     */
    public void copy(PojoResource resource) {
        for(Field field : PojoResource.class.getDeclaredFields()) {
            Method setter = getSetterMethodFromField(field);
            Method getter = getGetterMethodFromField(field);

            try {
                if(Collection.class.isAssignableFrom(field.getType())) {
                    Collection c = (Collection) getter.invoke(this);
                    c.addAll((Collection) getter.invoke(resource));
                } else {
                    setter.invoke(this, copyIfNotNull(getter.invoke(this), getter.invoke(resource)));
                }
            } catch(Exception e) {
                System.err.println(e);
            }
        }
    }

    /**
     * if Object o2 is null, don't copy, otherwise copy that value to Object o1
     *
     * @param o1
     * @param o2
     * @return
     */
    private Object copyIfNotNull(Object o1, Object o2) {
        if(o2 != null) {
            return o2;
        }

        return o1;
    }

    /**
     * Gets respective setter method based on the field name.
     *
     * @param field
     * @return
     */
    private Method getSetterMethodFromField(Field field) {
        try {
            return PojoResource.class.getMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), field.getType());
        } catch(NoSuchMethodException e) {
            System.err.println(e);
        }

        return null;
    }

    /**
     * Gets respective getter method based on the field name.
     *
     * @param field
     * @return
     */
    private Method getGetterMethodFromField(Field field) {
        try {
            return PojoResource.class.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
        } catch(NoSuchMethodException e) {
            System.err.println(e);
        }

        return null;
    }
}