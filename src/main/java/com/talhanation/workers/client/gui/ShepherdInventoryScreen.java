package com.talhanation.workers.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.workers.Main;
import com.talhanation.workers.entities.MinerEntity;
import com.talhanation.workers.entities.ShepherdEntity;
import com.talhanation.workers.inventory.ShepherdInventoryContainer;
import com.talhanation.workers.inventory.WorkerInventoryContainer;
import com.talhanation.workers.network.MessageMineDepth;
import com.talhanation.workers.network.MessageOpenGuiShepherd;
import com.talhanation.workers.network.MessageSheepCount;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ShepherdInventoryScreen extends WorkerInventoryScreen{

    private final ShepherdEntity shepherd;
    private int count;

    private static final int fontColor = 4210752;

    public ShepherdInventoryScreen(WorkerInventoryContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.shepherd = (ShepherdEntity) container.getWorker();
    }

    @Override
    protected void init(){
        super.init();
        //Count
        addButton(new Button(leftPos + 10, topPos + 60, 8, 12, new StringTextComponent("<"), button -> {
                this.count = shepherd.getMaxSheepCount();
                if (this.count != 0) {
                    this.count--;
                    Main.SIMPLE_CHANNEL.sendToServer(new MessageSheepCount(this.count, shepherd.getUUID()));
                }
        }));

        addButton(new Button(leftPos + 10 + 30, topPos + 60, 8, 12, new StringTextComponent(">"), button -> {
                this.count = shepherd.getMaxSheepCount();
                if (this.count != 32) {
                    this.count++;
                    Main.SIMPLE_CHANNEL.sendToServer(new MessageSheepCount(this.count, shepherd.getUUID()));
                }
        }));
    }


    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        int k = 79;//right left
        int l = 19;//hight

        String count = String.valueOf(shepherd.getMaxSheepCount());
        font.draw(matrixStack, "max. Sheep's:", k - 80, l + 35, fontColor);
        font.draw(matrixStack, count, k - 55, l + 45, fontColor);
    }

}