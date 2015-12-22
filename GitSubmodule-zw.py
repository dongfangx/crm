import subprocess
import os, sys

BASE_DIR = os.path.dirname(os.path.dirname(__file__))

def common_clear(submodule_directory, version):
	print " ----- "  + submodule_directory
	common_shell = ['cd '+ submodule_directory,
	'git checkout ' + version,
	'cd ..',
	'git add '+submodule_directory,
	'git commit -m "moved ' + submodule_directory + ' to ' + version + '"']
	dr = submodule_directory
	os.chdir(dr)
	os.system("git checkout " + version)
	os.chdir(sys.path[0])
	print " ======= "  + submodule_directory
	os.system('git add '+submodule_directory)
	os.system('git commit -m "moved ' + submodule_directory + ' to ' + version + '"')
	os.system('git push')


if __name__ == '__main__':
	""" change commands and add shell"""
	commands = [
	["git submodule add --force https://git.oschina.net/dongfangx/nutz-sys.git src/main/java/com/ly/sys/", 'v1.1'],
	["git submodule add --force https://git.oschina.net/dongfangx/nutz-common.git src/main/java/com/ly/comm/", 'v1.1'],
	["git submodule add --force https://git.oschina.net/dongfangx/beetl-bjui-sys.git src/main/webapp/WEB-INF/sys/", 'v1.1'],
	["git submodule add --force https://git.oschina.net/dongfangx/sys-resources.git src/main/webapp/sys/", 'v1.3'],
	["git submodule add --force https://git.oschina.net/dongfangx/BJUI.git src/main/webapp/BJUI/", "release_v1.2"]]

	for cmd in commands:
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