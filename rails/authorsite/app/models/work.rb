class Work < ActiveRecord::Base
    
    has_many :human_work_relationships
    has_many :humans, :through => :human_work_relationships
end
