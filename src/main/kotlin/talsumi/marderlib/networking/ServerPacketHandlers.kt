/*
 * MIT License
 *
 *  Copyright (c) 2022 Talsumi
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 *
 */

package talsumi.marderlib.networking

import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.registry.Registry
import talsumi.marderlib.content.IUpdatableBlockEntity
import talsumi.marderlib.content.IUpdatableEntity

object ServerPacketHandlers {

    fun register()
    {
        ServerPlayNetworking.registerGlobalReceiver(ClientPacketsOut.request_entity_update, ::receiveRequestEntityUpdatePacket)
        ServerPlayNetworking.registerGlobalReceiver(ClientPacketsOut.request_block_entity_update, ::receiveRequestBlockEntityUpdatePacket)
    }

    private fun receiveRequestEntityUpdatePacket(server: MinecraftServer, player: ServerPlayerEntity, handler: ServerPlayNetworkHandler, buf: PacketByteBuf, responseSender: PacketSender)
    {
        val ent = player.world.getEntityById(buf.readInt())

        server.execute {
            if (ent is IUpdatableEntity)
                ServerPacketsOut.sendUpdateEntityPacket(ent, player)
        }
    }

    private fun receiveRequestBlockEntityUpdatePacket(server: MinecraftServer, player: ServerPlayerEntity, handler: ServerPlayNetworkHandler, buf: PacketByteBuf, responseSender: PacketSender)
    {
        val pos = buf.readBlockPos()
        val type = Registry.BLOCK_ENTITY_TYPE.get(buf.readIdentifier())

        server.execute {
            val be = player.world.getBlockEntity(pos, type).orElse(null) ?: return@execute

            if (be is IUpdatableBlockEntity)
                ServerPacketsOut.sendUpdateBlockEntityPacket(be, player)
        }
    }
}