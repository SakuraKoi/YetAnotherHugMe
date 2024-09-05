package dev.sakurakooi.yetanotherhugme.mixin;

import dev.sakurakooi.yetanotherhugme.YetAnotherHugMe;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    private BufferBuilderStorage bufferBuilders;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;checkEmpty(Lnet/minecraft/client/util/math/MatrixStack;)V", ordinal = 0))
    public void render(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2) {
        if (camera.isThirdPerson())
            return;
        // TODO check should render
        MatrixStack matrices = new MatrixStack();
        Vec3d vec3d = camera.getPos();
        VertexConsumerProvider.Immediate immediate = this.bufferBuilders.getEntityVertexConsumers();
        YetAnotherHugMe.INSTANCE.setRenderingPlayer(true);
        renderEntity(camera.getFocusedEntity(), vec3d.x, vec3d.y, vec3d.z, tickCounter.getTickDelta(false), matrices, immediate);
        YetAnotherHugMe.INSTANCE.setRenderingPlayer(false);
    }

    @Shadow
    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
    }

}