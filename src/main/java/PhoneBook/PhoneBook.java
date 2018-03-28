package PhoneBook;

import java.util.*;
import java.util.regex.Pattern;

public class PhoneBook {

    private Map<String, Set<String>> storage = new HashMap<>();
    private Pattern phonePattern = Pattern.compile("((\\+\\d+)?\\(?\\d{3,4}\\)?\\d{3}-?\\d{2}-?\\d{2})|(\\*\\d+#)");
    // Этот паттерн пропускает номера телеонов следующих видов:
    // +7(999)999-99-99 (различные вариации с - / () и без них)
    // *999999#

    public boolean addPerson(String personName, Set<String> phones) {
        if (storage.containsKey(personName)) return false;
        // Проверяем, что человека еще нет в телефонной книге
        for (String phone : phones) {
            if (!phonePattern.matcher(phone).matches()) return false;
            // Проверяем, что все телефоны корректны
        }
        storage.put(personName, phones);
        return true;
    }

    public boolean addPhone(String personName, String phone) {
        if (!phonePattern.matcher(phone).matches() || !storage.keySet().contains(personName)) return false;
        // Проверяем, что человек есть в телефонной книге, а так же, что телефон корректен
        this.storage.get(personName).add(phone);
        return true;
    }

    public boolean removePerson(String personName) {
        if (!storage.keySet().contains(personName)) return false;
        storage.remove(personName);
        return true;
    }

    public boolean removePhone(String personName, String phone) {
        if (!storage.keySet().contains(personName) || !storage.get(personName).contains(phone)) return false;
        storage.get(personName).remove(phone);
        return true;
    }

    public Set<String> findOfPersonName(String personNameForFind) {
        if (!storage.keySet().contains(personNameForFind)) throw new IllegalArgumentException("Person not find");
        return storage.get(personNameForFind);
    }

    public List<String> findOfPhone(String phoneForFind) {
        List<String> resultFind = new ArrayList<>();
        storage.forEach((String person, Set<String> phones) -> {
            // Перебираем одновременно и людей и их телефонные номера
            for (String phone : phones) {
                // Перебираем телефонные номера у конкретного человека
                if (phone.equals(phoneForFind)) {
                    // Если нашли -- добавляем человека в результат и выходим из внутреннего цикла
                    resultFind.add(person);
                    break;
                }
            }
        });
        if (resultFind.isEmpty()) throw new IllegalArgumentException("Phone not find");
        return resultFind;
    }

    public Map<String, Set<String>> getStorage() {
        return storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneBook phoneBook = (PhoneBook) o;
        return storage.equals(phoneBook.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storage);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        storage.forEach((String person, Set<String> phones) -> {
            result
                    .append(person).append(": ")
                    .append(phones.toString().replaceAll("(\\[|\\])", ""))
                    // Удаляем из строкового представления все []
                    .append("\n");
        });
        return result.toString();
    }
}