# Krai
Krai is a self-reflective data node built in plain Java.
Each node stores its own data (across multiple primitive types), a reference to a “protocol” (a parent or governing node), and a dynamic array of links to other nodes.

It’s designed as an experiment in recursive structure, reflexive data modeling, and non-linear linking — basically a personal attempt at defining a data type that can describe itself.
Why this exists
I wanted a compact, understandable object that mixes data, topology and a notion of “governing node” (protocol).

Then I decided, why stop? I proceeded to work on my own ai model.

| Field        | Type     | Description                             |
| :----------- | :------- | :-------------------------------------- |
| dataInt      | int      | integer payload (default 0)             |
| dataDouble   | double   | floating-point payload (default 0.0)    |
| dataBool     | boolean  | boolean payload (default false)         |
| dataString   | String   | string payload (default empty)          |
| dataChar     | char     | character payload (default '\0')        |
|  protocol    | Krai     | reference to a governing or parent node |
| link         | Krai[]   | array of connected nodes                |

Constructors:
All constructors initialize a Krai node with specific data and connections.

Krai()

Default empty node. All fields zeroed or null, link array empty.

Krai(int d, Krai[] links, Krai p)

Integer node. Stores d in dataInt, copies links, sets parent as p.

Krai(double d, Krai[] links, Krai p)

Same as above, but stores a double.

Krai(boolean b, Krai[] links, Krai p)

Boolean variant.

Krai(String s, Krai[] links, Krai p)

String variant.

Krai(char c, Krai[] links, Krai p)

Character variant.

Protocol operations :

protocol_equals(Krai protocol)
Returns the given protocol if both nodes have equal dataInt values, else returns null.
(Intended as a minimal check for structural equality.)

protocol_ROLARI(int a, Krai protocol) //Replace-Or-Link-And-Reintegrate
Mutates the protocol’s link array by swapping a child link with this.
It’s an experimental method for “re-ordering” or “relinking” a structure based on an index a.
Returns the modified protocol.

protocol_diffsim(Krai protocol, int a)
Compares the current node’s link at index a with each link in the given protocol.
Finds the most similar one (by smallest integer difference) and applies a protocol_ROLARI at that position.




