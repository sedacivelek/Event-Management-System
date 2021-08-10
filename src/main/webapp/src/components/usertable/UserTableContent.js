import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import React from "react";
import IconButton from "@material-ui/core/IconButton";
import PersonAddRoundedIcon from '@material-ui/icons/PersonAddRounded';
import MapTwoToneIcon from "@material-ui/icons/MapTwoTone";
export default function UserTableContent(props){

    function createTableRow(row){
        return(
            <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                {props.columns.map((column) => {
                    return (
                        createTableCell(row,column)
                    );
                })}
            </TableRow>
        );
    }

    function createApplyIcon(onClick,eventId) {
        return(<IconButton onClick={()=>onClick(eventId)}><PersonAddRoundedIcon/></IconButton>)
    }
    function createMapIcon(onClick,eventId){
        return(
            <IconButton onClick={() => onClick(eventId)}><MapTwoToneIcon/></IconButton>
        )
    }


    function createTableCell(row,column){
        let value = row[column.id];
        if(column.id === "apply"){
            value = createApplyIcon(column.onClick,row.eventId);
        }
        if(column.id === "address"){
            value = createMapIcon(column.onClick,row.eventId);
        }
        return(
            <TableCell key={column.id} align={column.align}>
                {value}
            </TableCell>
        );
    }

    return(
        <TableBody>
            {props.rows.slice(props.page * props.rowsPerPage, props.page * props.rowsPerPage + props.rowsPerPage).map((row) => {
                return (
                    createTableRow(row)
                );
            })}
        </TableBody>
    );
}