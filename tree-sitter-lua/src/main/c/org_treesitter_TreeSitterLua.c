
#include <jni.h>
void *tree_sitter_lua();
/*
 * Class:     org_treesitter_TreeSitterLua
 * Method:    tree_sitter_lua
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterLua_tree_1sitter_1lua
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_lua();
}
