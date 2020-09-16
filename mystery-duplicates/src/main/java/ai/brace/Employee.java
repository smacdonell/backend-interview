package ai.brace;

public class Employee
{
    public String firstName;
    public String middleInitial;
    public String lastName;
    public String socialSecurityNumber;

    public Employee(String _lastName, String _firstName, String _middleInitial, String _socialSecurityNumber)
    {
        firstName = _firstName;
        middleInitial = _middleInitial;
        lastName = _lastName;
        socialSecurityNumber = _socialSecurityNumber;
    }

    /**
     * Make sure equals() is overridden with string values.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        Employee e = (Employee) obj;
        return (this.firstName.equals(e.firstName) && this.middleInitial.equals(e.middleInitial)
                && this.lastName.equals(e.lastName) && this.socialSecurityNumber.equals(e.socialSecurityNumber));
    }

    /**
     * Adding override for hashCode() to make sure hashCode is generated based on the string field values.
     * @return
     */
    @Override
    public int hashCode() {
        return 1 * (this.firstName.hashCode() + this.middleInitial.hashCode()
                + this.lastName.hashCode() + this.socialSecurityNumber.hashCode());
    }
}
