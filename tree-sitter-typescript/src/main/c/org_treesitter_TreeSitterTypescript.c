
#include <jni.h>
void *tree_sitter_tsx();
/*
 * Class:     org_treesitter_TreeSitterTypescript
 * Method:    tree_sitter_tsx
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterTypescript_tree_1sitter_1typescript
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_tsx();
}
