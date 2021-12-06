package net.quiltservertools.interdimensional.portals.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.quiltservertools.interdimensional.portals.InterdimensionalPortals;
import net.quiltservertools.interdimensional.portals.networking.NetworkManager;

import java.util.HashMap;
import java.util.Map;

public class ClientManager {
    private static final ClientManager instance = new ClientManager();

    public static ClientManager getInstance() {
        return instance;
    }

    private final Map<BlockPos, Integer> positions = new HashMap<>();

    private void addBlock(BlockPos pos, int color) {
        positions.put(pos, color);
    }

    public void clear() {
        positions.clear();
    }

    public int getColorAtPosition(BlockPos pos) {
        return positions.get(pos);
    }

    public boolean contains(BlockPos pos) {
        return positions.containsKey(pos);
    }

    public void register() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkManager.SYNC_PORTALS, ((client, handler, buf, responseSender) -> {
            var pos = buf.readBlockPos();
            var color = buf.readInt();
            if (client.world == null) return;
            System.out.println("No client world");
            var direction = buf.readInt();
            Direction.Axis axis;
            switch (direction) {
                case 1 -> axis = Direction.Axis.Z;
                case 2 -> axis = Direction.Axis.Y;
                default -> axis = Direction.Axis.X;
            }
            client.world.setBlockState(pos, InterdimensionalPortals.portalBlock.getDefaultState().with(Properties.HORIZONTAL_AXIS, axis));
            addBlock(pos, color);
        }));
    }
}
