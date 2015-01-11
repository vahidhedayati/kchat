package kchat

import grails.plugin.wschat.ChatRoomList

class ChatterController {
	def userService

	def index() {
		def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
		def curl= g.createLink(controller: 'chatter', action: 'index', absolute: 'true' )

		if (session.user) {
			def dbSupport=grailsApplication.config.wschat.dbsupport ?: 'yes'
			def chatuser=userService.returnRealUser(session.user)
			chatuser=chatuser.trim().replace(' ', '_').replace('.', '_')
			//session.wschatuser=chatuser
			def dbrooms
			def room=grailsApplication.config.wschat.rooms[0]
			if (dbSupport.toString().toLowerCase().equals('yes')) {
				dbrooms=ChatRoomList?.get(0)?.room
			}
			if (dbrooms) {
				room=dbrooms
			} else if (!room && !dbrooms) {
				room='wschat'
			}

			//session.wschatroom=room

			[chatuser:chatuser, room:room]
		}else{
			session.lastURL=curl
			flash.message="You must be logged in to use this feature"

		}
	}


	def index2() {
		if (session.user) {
			def chatTitle=grailsApplication?.config?.wschat.title ?: 'Grails Websocket Chat'
			def chatHeader=grailsApplication?.config?.wschat.heading ?: 'Grails websocket chat'
			def hostname=grailsApplication?.config?.wschat.hostname ?: 'localhost:8080'
			def showtitle=grailsApplication.config.wschat.showtitle ?: 'yes'
			def chatuser=userService.returnRealUser(session.user)
			def dbSupport=grailsApplication.config.wschat.dbsupport ?: 'yes'
			chatuser=chatuser.trim().replace(' ', '_').replace('.', '_')
			session.wschatuser=chatuser
			def dbrooms
			def room=grailsApplication.config.wschat.rooms[0]
			if (dbSupport.toString().toLowerCase().equals('yes')) {
				dbrooms=ChatRoomList?.get(0)?.room
			}
			if (dbrooms) {
				room=dbrooms
			} else if (!room && !dbrooms) {
				room='wschat'
			}
			session.wschatroom=room
			render (plugin: 'wschat', view : '/wsChat/chat', model: [dbsupport:dbSupport.toLowerCase() , showtitle:showtitle.toLowerCase(), room:room, chatuser:chatuser, chatTitle:chatTitle,chatHeader:chatHeader, now:new Date(),hostname:hostname])
		}else{
			flash.message="You must be logged in to use this feature"
		}
	}
	
}
