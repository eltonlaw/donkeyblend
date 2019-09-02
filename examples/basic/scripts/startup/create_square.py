import bpy
bl_info = {"name": "some script", "description": "does something"}
print("hello")
context_name = bpy.context.object.name
context = bpy.data.objects.get(context_name)