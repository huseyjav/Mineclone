# Mineclone
Minecraft clone for learning Java and OpenGL.

  

Since I am learning both 3d graphics and java in the same project, there are some place-holder classes I implemented to simplify things for now 
but they will be cleaned up once project progresses little bit more

# WORKING
- Movement
- Basic procedural generation
- Collision physics
- Textures
# TODO
- Make a block registry to not store copies of same blocks since they are static and can be shared
- Change data type of chunk meshes to avoid copying when creating a GPU buffer
- Implement ray tracing function for attacking etc.
- Implement event bus to decouple ChunkManager and Renderer
- Hand use animation