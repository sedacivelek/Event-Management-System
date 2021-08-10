import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

export default class EventAddDialog extends Component{
    state = {
        inputData:{...this.props.data}
    }

    render() {
        return (
            <div>
                <Dialog open={this.props.isOpen} onClose={this.props.handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">{this.props.title}</DialogTitle>
                    <DialogContent>
                        {this.props.textFields.map(field => {
                            return (
                                <TextField margin="dense" id={field.id} label={field.label} type={field.type}
                                           onChange={this.handleInputChange}
                                />
                            );})}
                    </DialogContent>

                    <DialogActions>
                        <Button onClick={this.props.handleClose} color="primary">
                            Cancel
                        </Button>
                        <Button onClick={()=> {
                            this.props.onSubmit(this.state.inputData)
                        }} color="primary">
                            Submit
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }

    handleInputChange = (event) => {
        event.persist();
        this.setState(prevState => {
            let inputData = {...prevState.inputData};
            inputData[event.target.id] = event.target.value;
            return{...prevState,inputData:inputData};
        })
    }


}
