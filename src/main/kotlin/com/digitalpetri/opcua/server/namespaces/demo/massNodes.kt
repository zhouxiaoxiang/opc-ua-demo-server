package com.digitalpetri.opcua.server.namespaces.demo

import com.digitalpetri.opcua.milo.extensions.inverseReferenceTo
import org.eclipse.milo.opcua.sdk.server.nodes.UaFolderNode
import org.eclipse.milo.opcua.stack.core.Identifiers
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint
import java.util.concurrent.atomic.AtomicLong


fun DemoNamespace.addMassNodes() {
    val massFolder = UaFolderNode(
        nodeContext,
        NodeId(namespaceIndex, "Mass"),
        QualifiedName(namespaceIndex, "Mass"),
        LocalizedText("Mass")
    )

    nodeManager.addNode(massFolder)

    massFolder.inverseReferenceTo(
        Identifiers.ObjectsFolder,
        Identifiers.HasComponent
    )

    addMassInt32Nodes(massFolder.nodeId)
}

private fun DemoNamespace.addMassInt32Nodes(parentNodeId: NodeId) {
    val nextId = AtomicLong(0)

    for (i in 'A'..'Z') {
        val folderNode = addFolderNode(parentNodeId, i.toString())

        for (j in 0 until 1000) {
            val name = "%03d".format(j)

            val nodeId = NodeId(namespaceIndex, uint(nextId.getAndIncrement()))

            addVariableNode(folderNode.nodeId, name, nodeId)
        }
    }
}
