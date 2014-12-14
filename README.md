JHelp
=====

lab projects
Заданы интерфейсы:
public interface IPerson {
	String getName ();
	id getId ();
	String getAddress();
}

public interface IContact {
	id getId ();
	String getContact();
}

Приложение с графическим пользовательским интерфейсом, которое позволяет вести (отображать) список любых объектов, реализующих  интерфейс   IPerson (список контактов) и их контактных данных. Приложение должно обеспечивать следующие возможности:
1. Создавать объекты типа IPerson и добавлять их в список
2. Создавать объекты, реализующие интерфейс IContact  и добавлять их в список, связывая их с объектами типа IPerson
3. Удалять из списка объекты типа IPerson  (при этом должны удаляться и все связанные с ним объекты типа  IContact )
4. Удалять из списка объекты типа IContact (при этом в списке должен остаться объект типа IPerson, с которым связан данный объект)
5. Редактировать обьекты типа IPerson и IContact

Приложение состоить из двух главных классов
ProxyServer и ProxyClient
