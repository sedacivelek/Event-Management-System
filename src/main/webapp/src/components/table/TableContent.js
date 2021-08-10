import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import React from "react";
import IconButton from "@material-ui/core/IconButton";
import UpdateOutlinedIcon from '@material-ui/icons/UpdateOutlined';
import DeleteOutlineOutlinedIcon from '@material-ui/icons/DeleteOutlineOutlined';
import MapTwoToneIcon from '@material-ui/icons/MapTwoTone';

export default function TableContent(props){

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

    function createUpdateIcon(onClick,eventId){
        return(
            <IconButton onClick={() => onClick(eventId)}><UpdateOutlinedIcon/></IconButton>
        )
    }
    function createDeleteIcon(onClick,eventId){
        return(
            <IconButton onClick={() => onClick(eventId)}><DeleteOutlineOutlinedIcon/></IconButton>
        )
    }
    function createMapIcon(onClick,eventId){
        return(
            <IconButton onClick={() => onClick(eventId)}><MapTwoToneIcon/></IconButton>
        )
    }

    function createTableCell(row,column){
        let value = row[column.id];
        if(column.id === "update"){
            value = createUpdateIcon(column.onClick,row.eventId);
        }
        if(column.id==="delete"){
            value = createDeleteIcon(column.onClick,row.eventId);
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