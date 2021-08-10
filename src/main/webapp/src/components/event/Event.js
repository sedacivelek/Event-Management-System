import React, { Component } from 'react';
import TableWithPaging from "../table/TableWithPaging";
import Button from "@material-ui/core/Button";
import PlaylistAddOutlinedIcon from "@material-ui/icons/PlaylistAddOutlined";
import AddDialog from "../adddialog/AddDialog";
import axios from "axios";
import Snackbar from "@material-ui/core/Snackbar";
import Alert from "@material-ui/lab/Alert";
import EventAddDialog from "../adddialog/EventAddDialog";
import EventMap from "../map/EventMap";
import Geocode from "react-geocode";
export default class Event extends Component{

    eventDialogFields = [
        {id:"eventName", label:"Event Name",type:"text"},
        {id:"eventId", label:"Event Id",type:"text"},
        {id:"startDate", label:"Start Date",type:"datetime-local"},
        {id:"endDate", label:"End Date",type:"datetime-local"},
        {id:"quota", label:"Quota",type:"number"},
        {id:"address",label: "Address",type:"text"}
    ]
    eventUpdateDialogFields = [
        {id:"eventName", label:"Event Name",type:"text"},
        {id:"startDate", label:"Start Date",type:"datetime-local"},
        {id:"endDate", label:"End Date",type:"datetime-local"},
        {id:"quota", label:"Quota",type:"number"},
        {id:"address",label: "Address",type:"text"}
    ]

    constructor(props) {
        super(props);
        this.state = {
            isDialogOpen:false,
            rows:[],
            isUpdateDialogOpen:false,
            snackbarProperties:{
                isOpen:false,
                message:"",
                severity:""
            },
            mapMarker:{
                lat: 39.890469,
                lng: 32.775120
            },
            mapPosition:{
                lat: 39.890469,
                lng: 32.775120
            }
        }

    }


    componentDidMount() {
        const jwt = localStorage.getItem('cool-jwt');
        if(!jwt){
            this.props.history.push('/login');
        }
        axios.get("/events",{headers:{Authorization: 'Bearer '+ jwt}})
            .then(response => {this.setState({rows:response.data})})
    }

    handleDialogOpen = () => {
        this.setState({isDialogOpen:true})
    }
    handleDialogClose = () => {
        this.setState({isDialogOpen:false})
    }
    handleUpdateDialogOpen = (eventId) => {
        this.setState({
            isUpdateDialogOpen:true,
            clickedEventId:eventId
        })
    }

    handleUpdateDialogClose = () => {
        this.setState({isUpdateDialogOpen:false})
    }

    handleEventAdd=(eventInfo) => {
        const jwt = localStorage.getItem('cool-jwt');
        console.log(jwt);
        axios.post("/events",eventInfo,{headers:{Authorization: 'Bearer '+ jwt}})
            .then(response => {
                this.handleDialogClose();
                this.setState(prevState => ({rows:[...prevState.rows ,response.data]}));
                this.snackbarOpen("Event has been added successfully.","success");
            })
            .catch(error => {
                if(error.response.status === 400){
                    this.snackbarOpen(error.response.data.errors[0].defaultMessage,"error")
                }

            })
    }
    handleEventUpdate = (clickedEventId,data) => {
        const jwt = localStorage.getItem('cool-jwt');
        data.eventId = clickedEventId;
        axios.put("/events/"+clickedEventId,data,{headers:{Authorization: 'Bearer '+ jwt}})
            .then(response => {
                this.handleUpdateDialogClose();
                this.setState(prevState => ({rows:[...prevState.rows.filter((eventEntity) =>
                        eventEntity.eventId !== clickedEventId) ,response.data]}));
                this.snackbarOpen("Event has been updated successfully.","success");
            })
            .catch(error =>{
                if(error.response.status === 400){
                    this.snackbarOpen(error.response.data.errors[0].defaultMessage,"error");
                }
                if(error.response.status === 500){
                    this.snackbarOpen(error.response.data,"error");
                    this.handleUpdateDialogClose();
                }
            })
    }
    handleAddress =(clickedEventId) => {
        const jwt = localStorage.getItem('cool-jwt');
        axios.get("/events/"+clickedEventId,{headers:{Authorization: 'Bearer '+ jwt}})
            .then(addressResponse  => {
                Geocode.setApiKey("AIzaSyD2b21JUW-ET4zrY5_-JKhzAnbIwxh0vsY");
                Geocode.fromAddress(addressResponse.data.address).then(
                    response => {
                        const { lat, lng } = response.results[0].geometry.location;
                        this.setState(
                            prevState => {
                                let mapMarker = {...prevState.mapMarker}
                                mapMarker.lat =lat;
                                mapMarker.lng = lng;
                                let mapPosition =  {...prevState.mapPosition}
                                mapPosition.lat = lat;
                                mapPosition.lng = lng;
                                return {mapMarker,mapPosition};

                            }
                        )

                })
    })
    }


    snackbarOpen = (message,severity) => {
        this.setState(prevState => {
            let snackbarProperties = {...prevState.snackbarProperties}
            snackbarProperties.isOpen = true;
            snackbarProperties.message=message;
            snackbarProperties.severity=severity;
            return {snackbarProperties};
            })
    }
    snackbarClose = () => {
        this.setState(prevState => {
            let snackbarProperties = {...prevState.snackbarProperties}
            snackbarProperties.isOpen = false;
            snackbarProperties.message="";
            snackbarProperties.severity="";
            return {snackbarProperties};
        })
    }
    handleEventDelete = (eventId) => {
        const jwt = localStorage.getItem('cool-jwt');
        axios.delete("/events/"+eventId,{headers:{Authorization: 'Bearer '+ jwt}})
            .then(response =>{
                this.setState({
                    rows: this.state.rows.filter((eventEntity) =>
                    eventEntity.eventId !== eventId)
                })
                this.snackbarOpen("Event " + eventId + " has been deleted.","success")
            }).catch(error => {
            if(error.response.status === 500){
                this.snackbarOpen(error.response.data,"error");
                this.handleUpdateDialogClose();
            }
        })
    }

    updateDialogAdd = () => {
        this.handleUpdateDialogOpen();



    }




    render() {
        return(

        <div>
            <TableWithPaging rows={this.state.rows} onDelete={this.handleEventDelete} onUpdate={this.handleUpdateDialogOpen} onAddress={this.handleAddress}/>
            <Button variant="outlined" color="primary" size="medium" style={{float:"left"}} onClick={this.handleDialogOpen} startIcon={<PlaylistAddOutlinedIcon/>}> ADD EVENT </Button>
            <Snackbar open={this.state.snackbarProperties.isOpen} autoHideDuration={5000} onClose={this.snackbarClose}
                      anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
                <Alert onClose={this.snackbarClose} severity={this.state.snackbarProperties.severity}>
                    {this.state.snackbarProperties.message}
                </Alert>
            </Snackbar>
            <AddDialog title="Update Event" isOpen={this.state.isUpdateDialogOpen} eventId={this.state.clickedEventId}
                       onSubmit={this.handleEventUpdate} handleOpen = {this.handleUpdateDialogOpen} handleClose={this.handleUpdateDialogClose} textFields = {this.eventUpdateDialogFields} />

            <EventAddDialog title="Add Event" isOpen={this.state.isDialogOpen}
                         onSubmit={this.handleEventAdd} handleOpen = {this.handleDialogOpen} handleClose={this.handleDialogClose} textFields = {this.eventDialogFields} />
                         <br/> <br/>
                         <EventMap mapMarker={this.state.mapMarker} mapPosition={this.state.mapPosition}/>
        </div>

        );
    }
}
