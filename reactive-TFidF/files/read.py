import os

# define path of paste
pastePos = 'aclImdb_v1/pos'

# read all images
pathsPos = [ os.path.join(pastePos, name) for name in os.listdir(pastePos) ] 

paths = pathsPos 


f = open("forRead.txt","w+")
for i in range(len(paths)):
     f.write(paths[i]+"\n")

f.close()
