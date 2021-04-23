# Food-Coop-Warehouse

Food-coop warehouse management software.
## Installing, starting and stopping the application

Food-coop warehouse comes prepackaged with its maven wrapper. Go to the project directory.<br>
Building the app:
```
mvnw clean install
```
Starting the app:
```
mvnw -pl plugins spring-boot:run
```
Stopping the app: 
```
ctrl c
```
___

##Domain Driven Design

Domain Driven Design (DDD) describes an approach to model complex software. The term was coined by Eric Evans in 2003 
[[1]](#1). A domain model is collaboratively extracted from the business domain by software engineers and domain 
experts. The model maps core concepts and relationships in the domain language.<br>
This guide uses the terminology as defined in Eric Evans' "Domain-driven design reference" [[2]](#2).

### Ubiquitous language

A food-coop warehouse contains defined amounts of goods (**Produkt**), largely foodstuff. Each *Produkt* is
of a specific **Kategorie** (literally category), e.g. meat or vegetable. The current stock (**istLagerbestand**) of
each *Produkt* and the current target stock (**sollLagerbestand**) can be set by the buyer (**Einkäufer**). The 
*Einkäufer* can also define new units of measurement (**Einheit**, e.g. kg, liters), new kinds of *Produkt* and new 
kinds of *Kategorie* of goods.
---

### Domain model terms

**Produkt**<br>
Product: Individual products of wares in the warehouse. A *Produkt* has a name, should have an icon, belongs to a
*Kategory*, has an *istLagerbestand* and a *sollLagerbestand*.

**Kategorie**<br>
Specific category of *Produkt*. Each category has a name and should have an icon. For example, the warehouse might be 
sorted into meat, vegetables, noodles, grains, etc.

**Lagerbestand**<br>
The **istLagerbestand** amount of a *Produkt* in the warehouse.<br>
The **sollLagerbestand** amount of a *Produkt* in the warehouse.

**Einheit**<br>
The unit of measurement of a *Lagerbestand*.
---

#### Roles

**Rollen**<br>
Roles of food-coop members.

**Einkäufer**<br>
Buyer: keeps the warehouse stocked by buying from farmers and wholesaler.

---

#### Use cases

**Lagermanagement**<br>
A complete view of all stock that can be manipulated by the buyer (**Einkäufer**). This REST-API provides all categories
(**Kategorien**), each with its products (**Produkt**). Each product comes with its current stock (**istLagerbestand**) 
and target stock (**sollLagerbestand**). It is on the client to create a suitable table to view the information,
preferably including the option to collapse categories.

The *Einkäufer* may create new *Kategorien*, change their icons and delete them, if they contain no *Produkt*.

The *Einkäufer* may create new *Produkt*, add them to and update a category, add, update and delete an icon, and manage 
the *Lagerbestand*.

The *Einkäufer* may choose an existing *Einheit* for the *Lagerbestand* or create a new one. He may delete unused 
*Einheiten*.

The *Einkäufer* may define a *sollLagerbestand* (target stock) of a *Produkt* to match supply and expected demand. He 
should update the *istLagerbestand* based on an inventory before placing a new order at the *Großhandel*.

**Externe Bestellungsliste**<br>
The **Einkäufer** gets a list of all products(**Produkte**) with current stock levels(**istLagerbestand**) below target
stock levels (**sollLagerbestand**), and the amount(**Menge**) that is missing. The list infomration is encoded in JSON
and is supposed to be turned into a PDF document by the client.

---

### Bounded context

---

### Terminology of large-scale structure

---

### Patterns

---

## API

---

### Kategorien

#### Get all Kategorien

Get all *Kategorien* with all *Produkte* and their respective *Lagerbestand* in one JSON (in production the information 
will depend on the user role via authentication).

```
curl -X GET --location "http://localhost:8080/kategorien" -H "Content-Type: application/json"
``` 

#### Get one Kategorien

Returns the Kategorie with the specified id.

```

curl -X GET --location "http://localhost:8080/kategorien/6abeec3f-fdc4-49b1-b64e-e005b45051cb" \
    -H "Content-Type: application/json"
```

#### New Kategorien

Add a Kategorie.

```
curl -X POST --location "http://localhost:8080/kategorien" \
-H "Content-Type: application/json" \
-d "{
\"id\":\"undefined\",
\"name\":\"Obst\",
\"icon\":\"data:image/png;base64,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\",
\"produkte\":[]
}"
```
#### Update Kategorien

Replace Kategorie with new one, same id.

```
curl -X PUT --location "http://localhost:8080/kategorien/1631c92e-a31e-45f2-89c9-32c90ff91b90" \
-H "Content-Type: application/json" \
-d "{
\"id\":\"1631c92e-a31e-45f2-89c9-32c90ff91b90\",
\"name\":\"Früchte\",
\"icon\":\"data:image/png;base64,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\",
\"produkte\":[]
}"
```

#### Delete Kategorien

If a Kategorie contains no Produkte, delete it.

```
curl -X DELETE --location "http://localhost:8080/produkte/5ff026e7-176d-4fe2-96ce-d3100033ac1e" \
    -H "Content-Type: application/json"
```

#### Get all Produkte

Returns a list of all Produkte.

```
curl -X GET --location "http://localhost:8080/produkte" \
    -H "Content-Type: application/json"
```

#### Get one Produkte

Get Produkt with the identifier ${id}.

```
curl -X GET --location "http://localhost:8080/produkte/30724b6f-01ec-4c47-aba7-1a5f2bc5f833" \
    -H "Content-Type: application/json"
```

#### New Produkte

Add a new Produkt to the database.

```
curl -X POST --location "http://localhost:8080/produkte" \
    -H "Content-Type: application/json" \
    -d "{
          \"id\":\"undefined\",
          \"name\":\"Äpfel\",
          \"kategorie\":\"1631c92e-a31e-45f2-89c9-32c90ff91b90\",
          \"lagerbestand\":{
            \"einheit\":{
              \"id\":\"d20b6519-bc2d-4e9f-b3e7-9bebc995f110\",
              \"name\":\"kg\"
            },
            \"istLagerbestand\":13,
            \"sollLagerbestand\":18
          }
        }"
```

#### Update Produkte

Replace Produkt with a new version (id must remain the same). (Yes, it is not good HATEOAS)

```
curl -X PUT --location "http://localhost:8080/produkte/5ff026e7-176d-4fe2-96ce-d3100033ac1e" \
    -H "Content-Type: application/json" \
    -d "{
          \"id\":\"5ff026e7-176d-4fe2-96ce-d3100033ac1e\",
          \"name\":\"Äpfel\",
          \"kategorie\":\"1631c92e-a31e-45f2-89c9-32c90ff91b90\",
          \"lagerbestand\":{
            \"einheit\":{
              \"id\":\"d20b6519-bc2d-4e9f-b3e7-9bebc995f110\",
              \"name\":\"kg\"
            },
            \"istLagerbestand\":9,
            \"sollLagerbestand\":18
          }
        }"
```
#### Delete Produkte

If a product is currently not in stock (if its Ist-Lagerbestand equals 0), delete the Produkt.

```
curl -X DELETE --location "http://localhost:8080/produkte/5ff026e7-176d-4fe2-96ce-d3100033ac1e" \
    -H "Content-Type: application/json"
```

#### Get all Einheiten

Get all Einheiten in the Database.

```
curl -X GET --location "http://localhost:8080/einheiten" -H "Content-Type: application/json"
``` 

#### Get one Einheiten

Returns specified Einheit as JSON, it it exists.

```
curl -X GET --location "http://localhost:8080/einheiten/d20b6519-bc2d-4e9f-b3e7-9bebc995f110\"
    -H "Content-Type: application/json"
```

#### New Einheiten

Adds a new Einheit to the database, provided its name is unique.
```
curl -X POST --location "http://localhost:8080/einheiten" \
-H "Content-Type: application/json" \
-d "{
\"id\":\"undefined\",
\"name\":\"Liter\"
}"
```

#### Delete Einheiten

Deletes an Einheit, if it exists and is not used by any Produkte

```
curl -X DELETE --location "http://localhost:8080/einheiten/835b53cb-a0a8-4076-8690-a1458aab0cb1" \
 -H "Content-Type: application/json"
``` 

---

## References
<a id="1">[1]</a>
Evans, Eric. Domain-driven design: tackling complexity in the heart of software. 
Addison-Wesley Professional, 2003.<br>
<a id="2">[2]</a>
Evans, Eric. "Domain-driven design reference." Definitions and Pattern Summaries. März (2015).