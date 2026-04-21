
#include <jni.h>
void *tree_sitter_powershell();
/*
 * Class:     org_treesitter_TreeSitterPowershell
 * Method:    tree_sitter_powershell
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterPowershell_tree_1sitter_1powershell
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_powershell();
}
