package com.s1mpie.gameserver.utils;

import com.s1mpie.gameserver.model.Coordinate;
import com.s1mpie.gameserver.model.WaterPiece;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UniversalUtil {
    public WaterPiece[] getPath(WaterPiece[] waterMap,Coordinate coordinate,String userId,int disX,int disY){
        if (coordinate == null || (disX == 3 && disY == 3)){
            return null;
        }
        int x = coordinate.getX();
        int y = coordinate.getY();
        if(x == disX && y == disY){
            return null;
        }
        WaterPiece[][] waterPieces = new WaterPiece[7][7];
        int len = waterMap.length;
        for(int i = 0;i < len;i++){
            waterPieces[waterMap[i].getX()][waterMap[i].getY()] = waterMap[i];
        }
        int[][] vis = new int[7][7];
        vis[x][y] = 1;
        int[] visX = new int[]{0,1,0,-1};
        int[] visY = new int[]{1,0,-1,0};
        Queue<Coordinate> que = new LinkedList<>();
        Coordinate[][] parentsCoordinate = new Coordinate[7][7];
        que.add(coordinate);
        while(!que.isEmpty()){
            Coordinate poll = que.poll();
            int cX = poll.getX();
            int cY = poll.getY();
            if(cX == disX && cY == disY){
                break;
            }
            for(int i = 0;i < 4;i++){
                int nX = cX + visX[i];
                int nY = cY + visY[i];
                if(nX >= 0 && nX <= 6 && nY >= 0 && nY <= 6 && vis[nX][nY] == 0 && waterPieces[nX][nY].getIfGet() == 1){
                    vis[nX][nY] = 1;
                    que.add(new Coordinate(nX,nY));
                    parentsCoordinate[nX][nY] = new Coordinate(cX,cY);
                }
            }
        }
        int nX = disX;
        int nY = disY;
        Stack<WaterPiece> ansPath = new Stack<>();
        ansPath.add(waterPieces[disX][disY]);
        while (true){
            if(nX == x && nY == y) {
                break;
            }
            int midX = nX;
            int midY = nY;
            ansPath.add(waterPieces[parentsCoordinate[nX][nY].getX()][parentsCoordinate[nX][nY].getY()]);
            nX = parentsCoordinate[midX][midY].getX();
            nY = parentsCoordinate[midX][midY].getY();
        }
        return ansPath.toArray(new WaterPiece[1]);
    }
}
