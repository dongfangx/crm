import subprocess
import os, sys
import shutil

BASE_DIR = os.path.dirname(os.path.dirname(__file__))

def common_clear(submodule_directory, version):

	dr = submodule_directory
	os.chdir(dr)
	os.system("git checkout " + version)
	os.system('git pull')
	os.chdir(sys.path[0])
	os.system('git add '+submodule_directory)
	os.system('git commit -m "moved ' + submodule_directory + ' to ' + version + '"')
	os.system('git push')

if __name__ == '__main__':
	""" change commands and add shell"""
	commands = [
	["git submodule add --force git@git.oschina.net:dongfangx/nutz-sys.git src/main/java/com/ly/sys", 'v2.0'],
	["git submodule add --force git@git.oschina.net:dongfangx/nutz-common.git src/main/java/com/ly/comm", 'v1.4'],
	["git submodule add --force git@git.oschina.net:dongfangx/beetl-bjui-sys.git src/main/webapp/WEB-INF/sys", 'v1.3'],
	["git submodule add --force git@git.oschina.net:dongfangx/sys-resources.git src/main/webapp/sys", 'v1.3'],
	["git submodule add --force git@git.oschina.net:dongfangx/BJUI.git src/main/webapp/BJUI", 'release_v1.2']]


	for cmd in commands:

		print '=========== '
		print '----------- ' + cmd[0]
		dir1 = cmd[0].split(' ')[-1]


		c1 =  "git rm --cached " + dir1
		print '===========' + c1 
		os.system(c1)
		subprocess.Popen(c1, stdout=subprocess.PIPE, env=os.environ, shell=True)
		p = subprocess.Popen(cmd[0], stdout=subprocess.PIPE, env=os.environ, shell=True)
		while True:
			line = p.stdout.readline()
			if not line:
				break
			print line
		err = p.wait()
		if err != 0:
			print "error shell: ", cmd, "git submodule failed"
		common_clear(cmd[0].split(' ')[-1], cmd[1])
		