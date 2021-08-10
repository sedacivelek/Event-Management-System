import React, {Component} from 'react';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableContainer from '@material-ui/core/TableContainer';
import TablePagination from '@material-ui/core/TablePagination';
import UserTableHeader from "./UserTableHeader";
import UserTableContent from "./UserTableContent";










export default class UserTable extends Component {

    columns = [
        {id:"eventId",label:"Event Id", minWidth:170},
        {id:"eventName",label:"Event Name", minWidth:170},
        {id:"startDate",label:"Start Date", minWidth:170},
        {id:"endDate",label:"End Date", minWidth:170},
        {id:"quota",label:"Quota", minWidth:170},
        {id:"address",label: "Address",minWidth: 170, onClick:this.props.onAddress},
        {id:"apply",label:"Apply event",onClick: this.props.onApply}
    ];

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage})
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({
            page:0,
            rowsPerPage: event.target.value
        })
    };
    constructor(props) {
        super(props);
        this.state = {
            page : 0,
            rowsPerPage:5,
            rows:[]
        }
    }



    render() {
        return (
            <Paper>
                <TableContainer>
                    <Table stickyHeader aria-label="sticky table">
                        <UserTableHeader columns={this.columns}/>
                        <UserTableContent rows={this.props.rows} columns={this.columns} page={this.state.page} rowsPerPage={this.state.rowsPerPage}
                        onApply={this.props.onApply}/>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[5,10, 25,{value:-1,label:'All'}]}
                    component="div"
                    count={this.props.rows.length}
                    rowsPerPage={this.state.rowsPerPage}
                    page={this.state.page}
                    onChangePage={this.handleChangePage}
                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                />
            </Paper>
        );
    }
}
