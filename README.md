# Food-Coop-Warehouse

Food-coop warehouse management software as a REST-service.
___

## Ubiquitous Language

A food-coop warehouse contains defined *amounts* of *goods* 
(largely foodstuff). Each good is of a specific *kind*, e.g. 
meat or vegetable. The *current inventory* of each good and 
the current *target inventory* can be set by the *admin*. 
The *admin* can also define new amounts (e.g. kg, liters), 
new goods and new kinds of goods.

**Good**<br>
Individual kind of wares in the warehouse.

**Amount**<br>
The number, weight or volume (depending on the good in question),
that the good is measured in. 

**Kind**<br>
Specific kind of good. For example, an  admin might sort 
foodstuff into animal and plant products.

**Inventory**<br>
The **current** amount of a good in the warehouse.<br>
The **traget** amount of a good in the warehouse.

