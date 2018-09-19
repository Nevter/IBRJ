# Implicative Bayesian Reasoner for Java (IBRJ)

IBRJ is a tool for demonstrating Implicative Bayesian Networks, 
build in Java. IBNs introduce classical and defeasible 
implication statements into a Bayesian Network, extending the 
expressivity of the network. 

***************************************************************

### Usage

With distribution of IBRJ there should be a *./build* script,
that will compile the program to the /bin/ directory. 

Once the program has been compiled, it can be run with the 
*/.ibr* script. This has two flags:
* *-cli* to run the program in CLI mode
* *-gui* to run the program in GUI mode

*Note:* Ensure that use has execute permissions on both scripts.

***************************************************************

### Licences

Implicative Bayesian Reasoner for Java (IBRJ) is licensed under
GNU Public License (GPL). You can read the license
in LICENSE.txt

However, BNJ uses subcomponents that are licensed
separately. Please take your time reading them.

| Component            | License File       | License Type
| ---------------------|--------------------|-------------
| lib/aima-core.jar    | LICENSE.aima       |  MIT
| lib/jxl.jar          | LICENSE.jxl        |  LGPL
| lib/log4j.jar        | LICENSE.log4j      |  Apache
| lib/log4j-core.jar   | LICENSE.log4j      |  Apache
| lib/metouia.jar      | LICENSE.metouia    |  LGPL
| lib/mysql.jar        | LICENSE.mysql      |  GPL
| lib/ojdbc14_g.jar    | LICENSE.oracle     |  (Proprietary)
| lib/openjgraph.jar   | LICENSE.openjgraph |  LGPL
| lib/postgresql.jar   | LICENSE.postgresql |  BSD
| lib/skinlf.jar       | LICENSE.skinlf     |  (Varies)
| Parts of COLT        | LICENSE.colt       |  CERN


For the license of Sean Luke's implementation of Mersenne Twister
(at MersenneTwisterFast.java in package edu.ksu.cis.kdd.util):
See the file comment for details. (It's a BSD type)

