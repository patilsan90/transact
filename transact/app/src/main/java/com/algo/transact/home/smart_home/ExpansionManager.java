package com.algo.transact.home.smart_home;

import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;
import com.algo.transact.home.smart_home.beans.Room;

import java.util.ArrayList;

/**
 * Created by patilsp on 10/17/2017.
 */

public class ExpansionManager implements View.OnClickListener {

    private final LinearLayout viewParentHolder;
    private final int window_width;
    private final ScrollView svScroll;
    SmartHomeActivity context;

    ArrayList<Room> rooms;
    private int prevExpandedViewIndex = -1;
    ArrayList<LinearLayout> roomsViewsList = new ArrayList<>();
    boolean[] roomExpandStatus;// = new ArrayList<>();
    int[] roomRowMapping;
    ArrayList<LinearLayout> roomsRowsList = new ArrayList<>();

    public ExpansionManager(SmartHomeActivity context, ArrayList<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
        viewParentHolder = (LinearLayout) context.findViewById(R.id.smart_home_ll_expandable_flow_view);
        svScroll = (ScrollView) context.findViewById(R.id.smart_home_sv_scroll_view);

        Point size = new Point();
        context.getWindow().getWindowManager().getDefaultDisplay().getSize(size);
        window_width = size.x;
        roomExpandStatus = new boolean[rooms.size()];
        roomRowMapping = new int[rooms.size()];

        for (int i = 0; i < rooms.size(); i++)
            roomExpandStatus[i] = false;

        int i;
        for (i = 0; i < rooms.size(); i += 2) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setId(45 + i);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout view = createRoomsView(i);
            roomRowMapping[i] = roomsRowsList.size();
            roomsViewsList.add(view);
            linearLayout.addView(view);

            if ((i + 1) < rooms.size()) {
                view = createRoomsView(i + 1);
                roomsViewsList.add(view);
                linearLayout.addView(view);
                roomRowMapping[i + 1] = roomsRowsList.size();
            }
            roomsRowsList.add(linearLayout);
            viewParentHolder.addView(linearLayout);
        }
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setId(45 + i);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        roomsRowsList.add(linearLayout);
        viewParentHolder.addView(linearLayout);

        for (i = 0; i < roomRowMapping.length; i++) {
            Log.d(AppState.TAG, i + "  MAPPING " + roomRowMapping[i]);
        }
    }

    public LinearLayout createRoomsView(int index) {

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOnClickListener(this);
        linearLayout.setId(index + 10);
        linearLayout.setPadding(5, 5, 5, 5);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.width = window_width / 2 - 10;
        linearParams.height = window_width / 2 + 30;
        //linearParams.height=200;
        linearLayout.setLayoutParams(linearParams);

        RoomFragment roomFragment = new RoomFragment(rooms.get(index));
        android.support.v4.app.FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.add(linearLayout.getId(), roomFragment, LinearLayout.class.getName());
        transaction.commit();

        return linearLayout;
    }


    public void onClick(View v) {
        int currentRoom = v.getId() - 10;
        int currentRow = (v.getId() - 10) / 2;
        //int currentRow = roomRowMapping[currentRoom];

        Log.d(AppState.TAG, "In Onclick  currentRoomIdxToExpand:: " + currentRoom + "  currentRowIdxToExpand:: " + currentRow);
        for (int i = 0; i < roomRowMapping.length; i++) {
            Log.d(AppState.TAG, i + "  MAPPING Before :: " + roomRowMapping[i]);
        }

        if (roomExpandStatus[currentRoom] == true) {
            shrinkRoomView(currentRoom);
            rearrangeAllViews();
        } else {


            for (int i = 0; i < roomExpandStatus.length; i++) {
                if (roomExpandStatus[i] == true) {
                    rearrangeAllViews();
                    break;
                }
            }
            /* shrinkAllRoomsExcept(currentRoom);
             *  TODO : Check if this method is required in future.
             */
            shrinkAllRooms();
            expandRoomView(currentRoom, currentRow);
            rearrangeAllViews(currentRoom, currentRow);

        }

        scrollToView(svScroll, roomsRowsList.get(roomRowMapping[currentRoom]));
        for (int i = 0; i < roomRowMapping.length; i++) {
            Log.d(AppState.TAG, i + "  MAPPING After :: " + roomRowMapping[i]);
        }
    }

    private void rearrangeAllViews() {

        int currentRoom = 0;
        int currentRow = 0;

        // Check whether it is last room or not
        // if it is last room then there is no need of rearranging rows.
        int totalRooms = rooms.size();
        if (currentRoom == (rooms.size() - 1))
            return;

        for (int i = currentRow; i < roomsRowsList.size(); i++)
            roomsRowsList.get(i).removeAllViews();


        int roomIdx = (currentRoom % 2 == 0) ? currentRoom : currentRoom - 1;

        if (roomIdx < 0)
            roomIdx = 0;

        int rowIdx = currentRow - 1;
        boolean toggleFlag = false;
        while (roomIdx < totalRooms) {
            if (toggleFlag == false) {
                rowIdx++;
                // roomsRowsList.get(rowIdx).removeAllViews();
                toggleFlag = true;
            } else {
                toggleFlag = false;
            }
            Log.i(AppState.TAG, "rowIdx " + rowIdx + "  roomIdx :: " + roomIdx);
            roomsRowsList.get(rowIdx).addView(roomsViewsList.get(roomIdx));
            roomRowMapping[roomIdx] = rowIdx;
            roomIdx++;
        }
    }

    private void rearrangeAllViews(int currentRoom, int currentRow) {

        // Check whether it is last room or not
        // if it is last room then there is no need of rearranging rows.
        int totalRooms = rooms.size();
        boolean toggleFlag = false;

        if (currentRoom % 2 != 0) {
            //Handling of odd location room i.e. second column
            roomsRowsList.get(currentRow).removeViewAt(0);
            currentRow++;
            for (int i = (currentRow); i < roomsRowsList.size(); i++)
                roomsRowsList.get(i).removeAllViews();

            Log.i(AppState.TAG, "In rearrangeAllViews  currentRoomIdxToExpand:: " + (currentRoom - 1) + "  currentRowIdxToExpand:: " + currentRow);
            roomsRowsList.get(currentRow).addView(roomsViewsList.get(currentRoom - 1));
            roomRowMapping[currentRoom - 1] = currentRow;
            toggleFlag = true;
        }
        int rowIdx = currentRow;
        int roomIdx = currentRoom + 1;
        while (roomIdx < totalRooms) {
            if (toggleFlag == false) {
                rowIdx++;
                roomsRowsList.get(rowIdx).removeAllViews();
                toggleFlag = true;
            } else {
                toggleFlag = false;
            }
            roomsRowsList.get(rowIdx).addView(roomsViewsList.get(roomIdx));
            roomRowMapping[roomIdx] = rowIdx;
            roomIdx++;
        }
    }

    private void shrinkAllRoomsExcept(int currentRoom) {
        int roomsCount = rooms.size();
        for (int i = 0; i < roomsCount; i++)
            if (roomExpandStatus[i] == true && i != currentRoom)
                shrinkRoomView(i);
    }

    private void shrinkAllRooms() {
        int roomsCount = rooms.size();
        for (int i = 0; i < roomsCount; i++)
            if (roomExpandStatus[i] == true)
                shrinkRoomView(i);
    }

    private void expandRoomView(int roomIndex, int currentRow) {
        if (roomIndex % 2 == 0 && (roomIndex + 1) < rooms.size())
            roomsRowsList.get(currentRow).removeViewAt(1);

        LinearLayout llCurrentRoomToExpand = roomsViewsList.get(roomIndex);
        ViewGroup.LayoutParams lp = llCurrentRoomToExpand.getLayoutParams();
        lp.width = window_width;
        lp.height = -2;
        llCurrentRoomToExpand.setLayoutParams(lp);
        roomExpandStatus[roomIndex] = true;
    }


    void shrinkRoomView(int roomIndex) {

        //This logic is to shrink currently clicked rooms view
        LinearLayout llPrevExpanded = roomsViewsList.get(roomIndex);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.width = window_width / 2 - 10;
        linearParams.height = window_width / 2 + 30;
        //linearParams.height=200;
        llPrevExpanded.setLayoutParams(linearParams);
        roomExpandStatus[roomIndex] = false;
    }

    private void scrollToView(final ScrollView scrollViewParent, final View view) {
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        scrollViewParent.smoothScrollTo(0, childOffset.y - 40);
    }

    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }
}
