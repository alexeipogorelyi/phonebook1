package PhoneBook;

import java.util.*;
import java.util.regex.Pattern;

public class PhoneBook {

    private Map<String, List<String>> storage = new HashMap<>();
    private Pattern phonePattern = Pattern.compile("((\\+\\d+)?\\(?\\d{3,4}\\)?\\d{3}-?\\d{2}-?\\d{2})|(\\*\\d+#)");

    public void addPerson(String personName, List<String> phones) {
        for (String phone : phones) {
            if (!phonePattern.matcher(phone).matches()) throw new IllegalArgumentException("Invalid phone");
        }
        storage.put(personName, phones);
    }

    public void addPhone(String personName, String phone) {
        if (!phonePattern.matcher(phone).matches()) throw new IllegalArgumentException("Invalid phone");
        if (!storage.keySet().contains(personName)) throw new IllegalArgumentException("Person not find");
        List<String> personPhones = new ArrayList<>(storage.get(personName));
        personPhones.add(phone);
        storage.put(personName, personPhones);
    }

    public void removePerson(String personName) {
        if (!storage.keySet().contains(personName)) throw new IllegalArgumentException("Person not find");
        storage.remove(personName);
    }

    public void removePhone(String personName, String phone) {
        if (!storage.keySet().contains(personName)) throw new IllegalArgumentException("Person not find");
        if (!storage.get(personName).contains(phone)) throw new IllegalArgumentException("Phone not find");
        List<String> phones = new ArrayList<>(storage.get(personName));
        phones.remove(phone);
        storage.put(personName, phones);
    }

    public List<String> findOfPersonName(String personNameForFind) {
        if (!storage.keySet().contains(personNameForFind)) throw new IllegalArgumentException("Person not find");
        return storage.get(personNameForFind);
    }

    public List<String> findOfPhone(String phoneForFind) {
        List<String> resultFind = new ArrayList<>();
        for (Map.Entry<String, List<String>> person : storage.entrySet()) {
            for (String phone : person.getValue()) {
                if (phone.equals(phoneForFind)) {
                    resultFind.add(person.getKey());
                    break;
                }
            }
        }
        if (resultFind.isEmpty()) throw new IllegalArgumentException("Phone not find");
        return resultFind;
    }

    public Map<String, List<String>> getStorage() {
        return storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneBook phoneBook = (PhoneBook) o;
        return storage == phoneBook.storage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(storage);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<String>> person : storage.entrySet()) {
            result
                    .append(person.getKey()).append(": ")
                    .append(person.getValue().toString().replaceAll("(\\[|\\])", ""))
                    .append("\n");
        }
        return result.toString();
    }
}
