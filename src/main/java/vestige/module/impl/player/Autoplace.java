package vestige.module.impl.player;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import vestige.event.Listener;
import vestige.event.impl.RenderEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.util.world.WorldUtil;

public class Autoplace extends Module {
   private final BooleanSetting placeUnderWhileOffground = new BooleanSetting("Place under while offground", false);

   public Autoplace() {
      super("Autoplace", Category.ULTILITY);
      this.addSettings(new AbstractSetting[]{this.placeUnderWhileOffground});
   }

   public boolean onDisable() {
      mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(1);
      return false;
   }

   @Listener(1)
   public void onRender(RenderEvent event) {
      if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         EnumFacing facing = mc.objectMouseOver.sideHit;
         boolean canPlaceOffGround = this.placeUnderWhileOffground.isEnabled() && !mc.thePlayer.onGround && WorldUtil.isAirOrLiquid(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ));
         if ((facing == EnumFacing.UP || facing == EnumFacing.DOWN) && !canPlaceOffGround) {
            mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(1);
         } else {
            mc.gameSettings.keyBindUseItem.pressed = true;
            mc.rightClickDelayTimer = 0;
         }
      }

   }
}
