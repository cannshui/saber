package dn.cs.saber.domain;

/**
 * Tag, a article belong to, such as Unix, Linux, C, Java, Git.
 *
 * @author hewen.deng
 */
public class Tag {

    private int id;

    private String name;

    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Tag: { id: " + id + ", name: " + name + ", count: " + count + " }";
    }

}
