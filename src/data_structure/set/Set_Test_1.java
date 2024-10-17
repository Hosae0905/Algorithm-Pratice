package data_structure.set;

public class Set_Test_1 {
    String name;
    int age;

    Set_Test_1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        result = prime * result * age;
        result = prime * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof Set_Test_1) && obj != null) {
            if (obj == this) {
                return true;
            }

            Set_Test_1 testObj = (Set_Test_1) obj;
            if (name == null) {
                if (testObj.name == null) {
                    return testObj.age == age;
                }

                return false;
            }

            if (testObj.name.equals(name) && testObj.age == age) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "[" + this.name + "] : [" + this.age + "]";
    }
}
