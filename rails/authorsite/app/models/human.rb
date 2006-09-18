class Human < ActiveRecord::Base
    
    set_table_name "humans"
    
    validates_presence_of :name, :message => "Name must be set"
    
    has_many :human_work_relationships
    has_many :works, :through => :human_work_relationships
    
end
