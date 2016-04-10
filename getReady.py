#!/usr/bin/python
# ...
# Andres Osorio - aosorio@uniandes.edu.co
# ...

import os,sys
import string
from optparse import OptionParser
#-----------------------------------------------------

parser = OptionParser()
parser.add_option("-a", type = "string", dest="target",
                  help="DIR", metavar="DIR" )

(options, args) = parser.parse_args()

if options.target is None:
        parser.error("please give a target directory")

#-----------------------------------------------------

app = options.target

cmds = []

cmds.append("git clone https://github.com/heroku/java-getting-started.git " + app )
cmds.append("cd " + app)
cmds.append("heroku create")
cmds.append("git push heroku master")
cmds.append("heroku ps:scale web=1")

for cmd in cmds:
	
	os.system(cmd)

