package de.dhbw.foodcoop.warehouse.plugins.pdf;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.vandeseer.easytable.drawing.Drawer;
import org.vandeseer.easytable.drawing.cell.VerticalTextCellDrawer;
import org.vandeseer.easytable.structure.cell.AbstractTextCell;
import org.vandeseer.easytable.structure.cell.VerticalTextCell;


@SuperBuilder(toBuilder = true)
public class CustomVerticalTextCell extends VerticalTextCell {

    
	@Override
    protected Drawer createDefaultDrawer() {
        return new CustomVerticalTextCellDrawer(this);
    }

}

