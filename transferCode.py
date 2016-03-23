#!/usr/bin/python
# ...
# Andres Osorio - aosorio@uniandes.edu.co
# ...

import os,sys
import string
from optparse import OptionParser
#-----------------------------------------------------

parser = OptionParser()
parser.add_option("-t", type = "string", dest="target",
                  help="DIR", metavar="DIR" )

parser.add_option("-s", type = "string", dest="src",
                  help="DIR", metavar="DIR" )

(options, args) = parser.parse_args()

if options.target is None:
        parser.error("please give a target directory")

if options.src is None:
        parser.error("please give a src directory")
	
#-----------------------------------------------------

src = options.src
target = options.target

java_ext = "java"
origin_source = ["/src/main/java/","/src/test/java/"]
cp_cmd = "echo cp -v "

for origin_path in origin_source:
	
	cmd = 'find '+ src + origin_path + ' -name \"*.' + java_ext + '\"'
	files = os.popen(cmd,'r').readlines()

	for f in files:
		import os.path
		if not os.path.exists(target + origin_path):
			print "creating subdirs"
			os.makedirs(target + origin_path)
		option = "."
		cp_cmd = "cp -v " + f[:-1] + " " + target + origin_path + option
		os.system(cp_cmd)
			
pom_xml = src + "/pom.xml"

if os.path.isfile(pom_xml):
	option = "."
	cp_cmd = "cp -v " + pom_xml + " " + target + "/" + option
	os.system(cp_cmd)
	
		

