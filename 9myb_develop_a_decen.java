import java.util.ArrayList;
import java.util.List;

interface Node {
    void addPeer(Node node);
    void sendMessage(String message);
    void receiveMessage(String message);
}

class PeerNode implements Node {
    private String id;
    private List<Node> peers;

    public PeerNode(String id) {
        this.id = id;
        this.peers = new ArrayList<>();
    }

    @Override
    public void addPeer(Node node) {
        peers.add(node);
    }

    @Override
    public void sendMessage(String message) {
        for (Node peer : peers) {
            peer.receiveMessage("[" + id + "] " + message);
        }
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}

class DecentralizedAppSimulator {
    private List<Node> nodes;

    public DecentralizedAppSimulator(int numNodes) {
        nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            Node node = new PeerNode("Node " + (i + 1));
            nodes.add(node);
        }

        // Connect nodes in a circular topology
        for (int i = 0; i < numNodes; i++) {
            Node node = nodes.get(i);
            Node nextNode = nodes.get((i + 1) % numNodes);
            node.addPeer(nextNode);
        }
    }

    public void simulate() {
        for (Node node : nodes) {
            node.sendMessage("Hello, decentralized world!");
        }
    }
}

public class DecentralizedAppSimulatorMain {
    public static void main(String[] args) {
        DecentralizedAppSimulator simulator = new DecentralizedAppSimulator(5);
        simulator.simulate();
    }
}