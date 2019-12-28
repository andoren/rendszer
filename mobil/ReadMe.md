# Routes and bodys:
## Get: http://localhost:8080/mobileNumbers
### RequestBody: -
### RespnoseBody: 
#### A mobilok darabszámát adja vissza.

## Get: http://localhost:8080/getMobiles
### RequestBody: -
### RespnoseBody: 
eg.:[
		{
			"manucafterer": "Samsung",
			"type": "Galaxy S8",
			"cameranumber": 2,
			"date_of_manucafturing": [
				2017,
				12,
				1
			],
			"imei": "354607076427917",
			"isnew": true,
			"screensize": 5.5,
			"color": "Blue"
		}
	]
### A mobils.json fájlban lévő mobilokat adja vissza.

## POST: http://localhost:8080/addMobile
### RequestBody: 
eg.: {
		"manucafterer":"Apple",
		"type":"Iphone X",
		"cameranumber":"4",
		"date_of_manucafturing":[2017,11,3],
		"imei":"868779036670818",
		"isnew":"true",
		"screensize":"5.8",
		"color": "White"
	}
	VAGY
	{
        "manucafterer": "Samsung",
        "type": "Galaxy S6",
        "cameranumber": 3,
        "date_of_manucafturing": [
            2015,
            4,
            28
        ],
        "imei": "354607076427917",
        "isnew": true,
        "screensize": 5.5,
        "color": "Blue"
    }
### RespnoseBody: 
	New mobile has been added: 868779036670818
### Rossz imei és már van ilyen imei-s telefon errort dobhat.

## Get: http://localhost:8080/getMobile/354607076427917
### RequestBody: -
### RespnoseBody: 
eg.: {
		"manucafterer": "Samsung",
		"type": "Galaxy S8",
		"cameranumber": 2,
		"date_of_manucafturing": [
			2017,
			12,
			1
		],
		"imei": "354607076427917",
		"isnew": true,
		"screensize": 5.5,
		"color": "Blue"
	}
#### Visszaadja az imei szám alapján a telefont. MobileNotFound dobhat.

## POST: http://localhost:8080/updateMobile
### RequestBody: 
eg.: {
		"manucafterer": "Samsung",
		"type": "Galaxy S8",
		"cameranumber": 3,
		"date_of_manucafturing": [
			2017,
			4,
			28
		],
		"imei": "354607076427917",
		"isnew": true,
		"screensize": 5.5,
		"color": "Blue"
	}
### RespnoseBody: 
eg.:{
		"manucafterer": "Samsung",
		"type": "Galaxy S8",
		"cameranumber": 3,
		"date_of_manucafturing": [
			2017,
			4,
			28
		],
		"imei": "354607076427917",
		"isnew": true,
		"screensize": 5.5,
		"color": "Blue"
	}
### --Ugyan azt adja vissza. vagy MobileNotFound

## Delete: http://localhost:8080/deleteMobile/354607076427917
### RequestBody: -
### RespnoseBody: 
#### The following mobile phone with this imei: 354607076427917 has been deleted
### MobileNotFoundot dobhat. 

## GET: http://localhost:8080/getMobilesBetweenYears/2017-2019
### RequestBody: -
### RespnoseBody: 
eg.: [
		{
			"manucafterer": "Apple",
			"type": "Iphone X",
			"cameranumber": 4,
			"date_of_manucafturing": [
				2017,
				11,
				3
			],
			"imei": "868779036670818",
			"isnew": true,
			"screensize": 5.8,
			"color": "White"
		}
]
### Visszaadja azokat a telefonokat mik az évek között vannak. InvalidYearRangeException dobhat

## GET: http://localhost:8080/getMobilesByManufacterer/Samsung
### RequestBody: -
### RespnoseBody: 
eg.:[
    {
        "manucafterer": "Samsung",
        "type": "Galaxy S6",
        "cameranumber": 3,
        "date_of_manucafturing": [
            2015,
            4,
            28
        ],
        "imei": "354607076427917",
        "isnew": true,
        "screensize": 5.5,
        "color": "Blue"
    }
]
### Visszaadja a kiválasztott gyártó telefonjait vagy hibát dob, hogy hibás enum és kilistázza a validokat.