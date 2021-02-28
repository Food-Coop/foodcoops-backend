# Food-Coop-Warehouse

Food-coop warehouse management software.
___

##Domain knowledge

A food-coop warehouse contains defined amounts (**Menge**) of goods (**Produkt**),
largely foodstuff. Each *Produkt* is of a specific **Kategorie** (literally
category), e.g. meat or vegetable. The current stock (**istLagerbestand**) of
each *Produkt* and the current target stock (**sollLagerbestand**) can be set by
the buyer (**Einkäufer**). The *Einkäufer* can also define new amounts (**Menge**,
e.g. kg, liters), new kinds of *Produkt* and newkinds of  *Kategorie* of goods.

### Ubiquitous language glossary

**Produkt**<br>
Product: Individual products of wares in the warehouse.

**Menge**<br>
Amount: The specific count, weight or volume (depending on the Produkt in 
question), that the *Produkt* is measured in. 

**Kategorie**<br>
Specific category of *Produkt*. For example, the warehouse might be sorted into 
meat, vegetables, noodles, grains, etc.

**Lagerbestand**<br>
The **istLagerbestand** amount of a *Produkt* in the warehouse.<br>
The **sollLagerbestand** amount of a *Produkt* in the warehouse.
---

###Roles

**Rollen**<br>
Roles of food coop members.

**Einkäufer**<br>
Buyer: keeps the warehouse stocked by buying from farmers and wholesaler.
---

###Use cases

**Ansicht Lagerbestand**<br>
A complete view of the all stock for the convenice of the buyer (**Einkäufer**). 
This REST-API provides the current stock (**istLagerbestand**) and target stock 
(**sollLagerbestand**) of each product (**Produkt**) sorted by category 
(**Kategorie**) and product as a JSON. It is on the client to create a suitable
table to view the information, preferably including the option to collapse 
categories.

**Externe Bestellungsliste**<br>
The **Einkäufer** gets a list of all products(**Produkte**) with current stock 
levels(**istLagerbestand**) below target stock levels (**sollLagerbestand**), 
and the amount(**Menge**) that is missing. The list infomration is encoded in 
JSON and is supposed to be turned into a PDF document by the client.

