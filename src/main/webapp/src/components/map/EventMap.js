import {
    withScriptjs,
    withGoogleMap,
    GoogleMap,
    Marker,
} from "react-google-maps";
import React,{Component} from "react";

export default class EventMap extends Component{

    constructor(props) {
        super(props);
        this.state = {
            mapPosition:{
                lat:39.890469,
                lng:32.775120
            },
            markerPosition:{
                lat:39.890469,
                lng:32.775120
            }
        }
    }
    render() {
        const MapWithAMarker = withScriptjs(withGoogleMap(props =>
            <GoogleMap
                defaultZoom={12}
                defaultCenter={{ lat: this.props.mapPosition.lat, lng: this.props.mapPosition.lng}}
            >
                <Marker
                    position={{ lat: this.props.mapMarker.lat, lng:this.props.mapMarker.lng}}
                />
            </GoogleMap>
        ));
        return (
            <MapWithAMarker
                googleMapURL=""
                loadingElement={<div style={{ height: `100%` }} />}
                containerElement={<div style={{ height: `400px` }} />}
                mapElement={<div style={{ height: `100%` }} />}
            />
        );
    }
}



